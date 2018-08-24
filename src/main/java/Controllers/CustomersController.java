package Controllers;

import db.DBHelper;
import models.Customer;
import models.Shop;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;

public class CustomersController {

    public CustomersController() {
        this.setupEndpoints();
    }

    private void setupEndpoints() {

        get("/customers", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            List<Customer> customers = DBHelper.getAll(Customer.class);
            model.put("template", "templates/customers/index.vtl");
            model.put("customers", customers);
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


        get("/customers/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("template", "templates/customers/create.vtl");
            return new ModelAndView(model, "templates/layout.vtl");
        }, new VelocityTemplateEngine());


        post("/customers", (req, res) -> {
            int shopId = Integer.parseInt(req.queryParams(("shop")));
            Shop shop = DBHelper.find(shopId, Shop.class);
            String customerName = req.queryParams("customerName");
            String customerAddress = req.queryParams("customerAddress");
            Customer customer = new Customer(customerName, customerAddress, shop);
            DBHelper.save(customer);
            res.redirect("/customers");
            return null;
        }, new VelocityTemplateEngine());


    }
}

