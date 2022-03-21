package com.example.getir.controller;

import com.example.getir.dao_impl.CustomerDaoImpl;
import com.example.getir.enums.ErrorCode;
import com.example.getir.exceptions.GetirLogicException;
import com.example.getir.model.Customer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Controller
public class CustomerController {
    //Will persist new customers
    //Will query all orders of the customer ( Paging sounds really nice )

    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    private final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe
    private final Base64.Decoder base64Decoder = Base64.getUrlDecoder(); //threadsafe

    @Autowired
    CustomerDaoImpl customerDaoImpl;

    private Map<String, String> validTokenMap = new ConcurrentHashMap<>();

    public Customer registerCustomer(Customer customer) throws GetirLogicException {
        try {
            Customer exist = customerDaoImpl.existCustomerByEmailOrPhone(customer.getEmail(), customer.getPhone());
            if (exist != null) {
                logger.info("User is exist!!!");
                throw new GetirLogicException(ErrorCode.DUPLICATE_USER);
            }
            return customerDaoImpl.saveCustomer(customer);
        } catch (GetirLogicException e) {
            logger.error(e.getMessage());
            throw new GetirLogicException(e.getMessage(), e.getErrorCode());
        }
    }

    public List<Customer> showAllCustomer() throws GetirLogicException {
        try {
            return customerDaoImpl.getCustomerAll();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GetirLogicException(e.getMessage(), ErrorCode.DATABASE);
        }
    }

    //validations
    public String loginValidation(String email, String pass) throws GetirLogicException {
        try {
            Customer user = customerDaoImpl.existCustomerByEmailAndPass(email, pass);
            if (user == null) {
                throw new GetirLogicException(ErrorCode.USER_NOT_EXISTS);
            }

            String token = createNewToken(email);
            validTokenMap.put(email, token);

            return token;
        } catch (GetirLogicException e) {
            throw new GetirLogicException(e.getMessage(), e.getErrorCode());
        }
    }

    public void tokenValidation(Map<String, String> headers) throws GetirLogicException,Exception {
        try {
            if(headers.containsKey("authorization") == false)
                throw new GetirLogicException(ErrorCode.EMPTY_TOKEN);

            for (var entry : headers.entrySet()) {
                if (entry.getKey().equalsIgnoreCase("authorization") && entry.getValue() != null) {
                    System.out.println("Header Name: " + entry.getKey() + " Header Value: " + entry.getValue());
                    String[] tokenArray = entry.getValue().split(Pattern.quote("."));
                    String email = new String(base64Decoder.decode(tokenArray[0]));

                    if (validTokenMap.isEmpty() == false && validTokenMap.get(email).equalsIgnoreCase(entry.getValue())) {
                        break;
                    } else {
                        throw new GetirLogicException(ErrorCode.INVALID_TOKEN);
                    }
                }
            }
        } catch (GetirLogicException cce) {
            throw new GetirLogicException(cce.getMessage(), cce.getErrorCode());
        }
        catch (ArrayIndexOutOfBoundsException outOfBoundsException) {
            throw new Exception(outOfBoundsException.getMessage());
        }
    }

    private String createNewToken(String email) {
        StringBuilder tokenbuilder = new StringBuilder();
        String token = new String();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        base64Encoder.encodeToString(randomBytes);
        token = tokenbuilder.append(base64Encoder.encodeToString(email.getBytes())).append(".").append(base64Encoder.encodeToString(randomBytes)).toString();

        return token;
    }
}
