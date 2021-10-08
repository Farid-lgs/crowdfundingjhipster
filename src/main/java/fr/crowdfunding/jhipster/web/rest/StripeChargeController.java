package fr.crowdfunding.jhipster.web.rest;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import fr.crowdfunding.jhipster.domain.StripeCharge;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class StripeChargeController {

    @PostMapping("/charge")
    public ResponseEntity<String> createCharge(@RequestBody StripeCharge stripeCharge) {
        System.out.println(stripeCharge);
        try {
            // for demonstrations sake, using .env file
//            Dotenv dotenv = Dotenv.load();

            // creating the charge
            Stripe.apiKey = "sk_test_51Jh7d8IFCde9fuycylNbFRx4yyFuR5YXdodIJr9VRCBmKx3myalnlrz9mt1Jcs0zNcDWHViJw2JS6sdzTldDR7Po00nxhXpDdr";//dotenv.get("SK_TEST_KEY");
            Charge charge = Charge.create(stripeCharge.getCharge());
            System.out.println(charge);
            System.out.println("*******************************");
            return new ResponseEntity<String>("Success", HttpStatus.CREATED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
