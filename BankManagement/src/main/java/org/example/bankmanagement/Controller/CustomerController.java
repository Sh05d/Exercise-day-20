package org.example.bankmanagement.Controller;

import org.example.bankmanagement.ApiResponse.ApiResponse;
import org.example.bankmanagement.Model.Customer;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    ArrayList<Customer> customers = new ArrayList<>();

    //get all customers
    @GetMapping("/get-customers")
    public ArrayList<Customer> getCustomers(){
        return customers;
    }

    //add new customer
    @PostMapping("/add-customer")
    public ApiResponse addCustomer(@RequestBody Customer newCustomer){
        if(newCustomer.getBalance()<0)
            return new ApiResponse("Balance can't be negative");
        for(Customer customer : customers){
            if(customer.getId().equals(newCustomer.getId()))
                return new ApiResponse("The user already has account");
        }
        customers.add(newCustomer);
        return new ApiResponse("Customer added successfully");
    }

    //update customer
    @PutMapping("/update-customer/{id}")
    public ApiResponse updateCustomer(@PathVariable String id,@RequestBody Customer updatedcCustomer){
        if(updatedcCustomer.getBalance() < 0)
            return new ApiResponse("Sorry can't update balance to negative");
        for(Customer customer : customers){
            if(customer.getId().equals(id)){ //to make sure not change id
                customer.setUsername(updatedcCustomer.getUsername());
                customer.setBalance(updatedcCustomer.getBalance());
                return new ApiResponse("customer info updated successfully");
            }
        }
        return new ApiResponse("Sorry can't update customer not found");
    }

    //delete customer
    @DeleteMapping("/delete-customer/{id}")
    public ApiResponse deleteCustomer(@PathVariable String id){
        for(int i=0; i< customers.size(); i++){
            if(customers.get(i).getId().equals(id)){
                customers.remove(i);
                return new ApiResponse("Customer deleted successfully");
            }
        }
        return new ApiResponse("Sorry can't delete customer not found");
    }

    //deposit money to customer
    @PutMapping("/deposit/{id}/{amount}")
    public ApiResponse deposit(@PathVariable String id,@PathVariable double amount){
        if(amount<0)
            return new ApiResponse("Amount should be positive");
        for(int i=0; i< customers.size(); i++){
            if(customers.get(i).getId().equals(id)){
                customers.get(i).setBalance(customers.get(i).getBalance() + amount);
                return new ApiResponse("Deposit successful current balance "+customers.get(i).getBalance());
            }
        }
        return new ApiResponse("Sorry can't deposit customer not found");
    }

    //withdraw money from customer
    @PutMapping("/withdraw/{id}/{amount}")
    public ApiResponse withdraw(@PathVariable String id,@PathVariable double amount){
        if(amount<0)
            return new ApiResponse("Amount should be positive");
        for(int i=0; i< customers.size(); i++){
            if(customers.get(i).getId().equals(id)){
                if(customers.get(i).getBalance()<amount)
                    return new ApiResponse("Sorry the amount is bigger than the balance");
                customers.get(i).setBalance(customers.get(i).getBalance() - amount);
                return new ApiResponse("Withdrawal successful current balance "+customers.get(i).getBalance());
            }
        }
        return new ApiResponse("Sorry customer not found");
    }
}
