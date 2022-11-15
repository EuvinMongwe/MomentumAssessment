/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.momentum.controller;

import com.momentum.email.EmailClient;
import com.momentum.jdbc.dao.persistence.entity.InvestorEntity;
import com.momentum.jdbc.dao.persistence.entity.LoginEntity;
import com.momentum.jdbc.dao.persistence.entity.ProductEntity;
import com.momentum.jdbc.service.AdminService;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author euvinmongwe
 */
@org.springframework.stereotype.Controller
@ComponentScan(basePackages = "com.momentum.controller")
public class Controller {

    /**
     * log
     */
    private final static Logger log = Logger.getLogger(Controller.class.getName());

    /**
     * adminService
     */
    private AdminService adminService;

    /**
     * emailClient
     */
    private EmailClient emailClient;

    /**
     *
     * @param adminService
     * @param emailClient
     */
    @Autowired
    public Controller(AdminService adminService, EmailClient emailClient) {
        this.adminService = adminService;
        this.emailClient = emailClient;
    }

    /**
     *
     * @param username
     * @param password
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public String Login(@RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            Model model,
            HttpSession request) {

        try {
            request.setAttribute("username", username);

            if ((username.length() > 3) || (password.length() > 3)) {

                for (LoginEntity login : adminService.getLogins(username)) {

                    if (password.equalsIgnoreCase(login.getPassword())) {

                        if (adminService.getProductInfo(username) != null) {
                            for (ProductEntity product : adminService.getProductInfo(username)) {

                                model.addAttribute("retirementBalance", product.getRetirementBalance());
                                model.addAttribute("savingBalance", product.getSavingBalance());

                            }
                            model.addAttribute("product", adminService.getProductInfo(username));
                            request.setAttribute("product", adminService.getProductInfo(username));
                        }

                        if (adminService.getProductInfo(username) != null) {
                            model.addAttribute("investor", adminService.getInvestorInfo(username));
                            request.setAttribute("investor", adminService.getInvestorInfo(username));
                        }
                        return "Withdrawal";
                    } else {
                        model.addAttribute("username", username);
                        model.addAttribute("failLogin", "Incorrect Credintials, please try again or click here to reset password");
                        return "HomePage";
                    }
                }
                model.addAttribute("username", "");
                model.addAttribute("failCred", "Sorry we don't have any investments linked to username : "+username);
                return "HomePage";
            } else {

                model.addAttribute("username", username);
                model.addAttribute("failCred", "Credintials entered did not meet requirement username must have more than 5 char try again");
                return "HomePage";
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
        }

        model.addAttribute("failCred", "Sorry we cannot proccess your request at the moment");
        return "HomePage";
    }

    /**
     * Will process withdrawal form update database and send confirmation email
     *
     * @param model
     * @param request
     * @param withdrawAmnt
     * @param investmentType
     * @return
     */
    @RequestMapping(value = "/processWithdrawal", method = RequestMethod.POST)
    public String processWithdrawal(Model model, HttpSession request,
            @RequestParam(value = "withdrawAmnt") String withdrawAmnt,
            @RequestParam(value = "investmentType") String investmentType) {

        try {
            List<ProductEntity> products = (List<ProductEntity>) request.getAttribute("product");
            List<InvestorEntity> investors = (List<InvestorEntity>) request.getAttribute("investor");
            Date brtday = new SimpleDateFormat("yyyy-MM-dd").parse(investors.get(0).getDob());

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(brtday);

            LocalDate today = LocalDate.now();

            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1;
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            LocalDate birthday = LocalDate.of(year, month, day);

            Period p = Period.between(birthday, today);

            double withAmount = Double.parseDouble(withdrawAmnt);

            ProductEntity product = new ProductEntity();
            product.setInvestorid(products.get(0).getInvestorid());

            if ((p.getYears() < 65) && investmentType.equalsIgnoreCase("Retirement")) {
                System.out.println("younger than " + 65);
                model.addAttribute("errorMsg", "You must be 65 years or older to withdraw your investment");
                model.addAttribute("product", products);
                model.addAttribute("investor", investors);
                return "Withdrawal";

            }

            if (investmentType.equalsIgnoreCase("Savings")) {
                System.out.println("investment  :  " + investmentType);
                double savingBalance = Double.parseDouble(products.get(0).getSavingBalance());
                if (withAmount > savingBalance) {

                    model.addAttribute("errorMsg", "You cannot withdraw amount greater than " + savingBalance);
                    model.addAttribute("product", products);
                    model.addAttribute("investor", investors);
                    return "Withdrawal";
                }

                double amountP = withAmount / savingBalance * 100;
                double balance = savingBalance - withAmount;

                if (amountP > 90) {
                    model.addAttribute("errorMsg", "You cannot withdraw more than 90% of the money you have");
                    model.addAttribute("product", products);
                    model.addAttribute("investor", investors);
                    return "Withdrawal";
                }

                product.setRetirementID(products.get(0).getRetirementID());
                product.setRetirementPreviousBalance(products.get(0).getRetirementPreviousBalance());
                product.setLastRetire(products.get(0).getLastRetire());
                product.setRetirementBalance(products.get(0).getRetirementBalance());
                product.setRetireDate(products.get(0).getRetireDate());
                
                product.setSavingID(products.get(0).getSavingID());
                product.setSavingPreviousBalance(products.get(0).getSavingBalance());
                product.setLastSaving(products.get(0).getSaveDate());
                product.setSavingBalance(String.valueOf(balance));
                product.setSaveDate(new Timestamp(new Date().getTime()));
                
                System.out.println("product   ---->    "+product);

                adminService.withdraw(product);

                emailClient.sendMail(investors.get(0).getEmail(),
                        "Momentum Investment Savings Withdrawal",
                        "Dear " + investors.get(0).getFirstname() + " " + investors.get(0).getLastname() + ", \n\n"
                        + "Your withdrawal was successful. "
                        + "\n Previous Balance : R" + product.getSavingPreviousBalance()
                        + "\n Current Balance : R" + product.getSavingBalance()
                        + "\n\nKind Regards,\n Momentum Investments",
                        "noreply@momentumInvestments.co.za");

                return "success";

            } else {
                System.out.println("investment  :  " + investmentType);
                double retirementBalance = Double.parseDouble(products.get(0).getRetirementBalance());
                if (withAmount > retirementBalance) {
                    model.addAttribute("errorMsg", "You cannot withdraw amount greater than " + retirementBalance);
                    model.addAttribute("product", products);
                    model.addAttribute("investor", investors);
                    return "Withdrawal";
                }

                double amountP = withAmount / retirementBalance * 100;
                double balance = retirementBalance - withAmount;
                if (amountP > 90) {
                    model.addAttribute("errorMsg", "You cannot withdraw more than 90% of the money you have");
                    model.addAttribute("product", products);
                    model.addAttribute("investor", investors);
                    return "Withdrawal";
                }

                product.setSavingID(products.get(0).getSavingID());
                product.setSavingPreviousBalance(products.get(0).getSavingPreviousBalance());
                product.setLastSaving(products.get(0).getLastSaving());
                product.setSavingBalance(products.get(0).getSavingBalance());
                product.setSaveDate(products.get(0).getSaveDate());
                product.setRetirementID(products.get(0).getRetirementID());
                product.setRetirementPreviousBalance(products.get(0).getRetirementBalance());
                product.setLastRetire(products.get(0).getRetireDate());
                product.setRetirementBalance(String.valueOf(balance));
                product.setRetireDate(new Timestamp(new Date().getTime()));
                
                System.out.println("product   ---->    "+product);

                adminService.withdraw(product);

                emailClient.sendMail(investors.get(0).getEmail(),
                        "Momentum Investment Retirement Withdrawal",
                        "Dear " + investors.get(0).getFirstname() + " " + investors.get(0).getLastname() + ", \n\n"
                        + "Your withdrawal was successful. "
                        + "\n Previous Balance : R" + product.getRetirementBalance()
                        + "\n Current Balance : R" + product.getRetirementBalance()
                        + "\n\nKind Regards,\n Momentum Investments",
                        "noreply@momentumInvestments.co.za");

                return "success";

            }

        } catch (ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("errorMsg", "Sorry we cannot proccess your request, please try again later");
            model.addAttribute("product", request.getAttribute("product"));
            model.addAttribute("investor", request.getAttribute("investor"));
            return "Withdrawal";
        }

    }

    /**
     * This method will retrieve username from HttpSession Generate password and
     * update LoginEntity Get investors details and send mail with credintials
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public String resetPassword(Model model,
            HttpSession request) {

        String username = (String) request.getAttribute("username");

        System.out.println("username---> " + username);

        try {

            String password = UUID.randomUUID().toString();

            if (adminService.getLogins(username) != null) {

                for (LoginEntity logins : adminService.getLogins(username)) {

                    if (username.equalsIgnoreCase(logins.getInvestorID())) {

                        if (adminService.getInvestorInfo(username) != null) {
                            for (InvestorEntity investor : adminService.getInvestorInfo(username)) {
                                System.out.println("investor    ---->    " + investor);
                                emailClient.sendMail(investor.getEmail(),
                                        "Momentum Investment Password Reset",
                                        "Dear " + investor.getFirstname() + " " + investor.getLastname() + ", \n\n"
                                        + "Your password reset was successful. \n\n Username :" + investor.getInvestorID()
                                        + "\n\n Password :" + password+"\n\nKind Regards,\nMomentumInvestments",
                                        "noreply@momentumInvestments.co.za");
                            }
                        }

                    }

                }

                LoginEntity login = new LoginEntity();
                login.setInvestorID(username);
                login.setPassword(password);
                System.out.println(" resetPassword---  " + login);
                adminService.resetPassword(login);
                model.addAttribute("username", "");
                model.addAttribute("resetPasssword", "Your password was successfully reset, check your mail for new credintials.. ");
                return "HomePage";
            } else {
                System.out.println("Else-------");
                model.addAttribute("username", "");
                model.addAttribute("failCred", "Sorry we could not find username : " + username + " In our database, click request call back");
                return "HomePage";
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, null, e);
        }
        model.addAttribute("username", "");
        model.addAttribute("failCred", "Sorry we cannot proccess your request at the moment");
        return "HomePage";
    }

}
