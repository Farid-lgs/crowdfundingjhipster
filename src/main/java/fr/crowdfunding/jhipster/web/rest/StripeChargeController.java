package fr.crowdfunding.jhipster.web.rest;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import fr.crowdfunding.jhipster.domain.StripeCharge;
import fr.crowdfunding.jhipster.service.ContributionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StripeChargeController {

    private static final int INDEX_AMOUNT = 0;
    private static final int INDEX_EMAIL = 1;
    private static final int INDEX_TOKEN = 2;
    private static final int INDEX_PROJECT_ID = 3;
    private static final int INDEX_USERNAME = 4;

    private final ContributionService contributionService;

    public StripeChargeController(ContributionService contributionService) {
        this.contributionService = contributionService;
    }

    @PostMapping("/charge")
    public ResponseEntity<String> createCharge(@RequestBody String datas) {

        String[] arrayDatasSplit = datas.split(" ");

        StripeCharge stripeCharge = new StripeCharge(Long.parseLong(arrayDatasSplit[INDEX_AMOUNT]), arrayDatasSplit[INDEX_EMAIL], arrayDatasSplit[INDEX_TOKEN]);

        try {
            // creating the charge
            Stripe.apiKey = "sk_test_51Jh7d8IFCde9fuycylNbFRx4yyFuR5YXdodIJr9VRCBmKx3myalnlrz9mt1Jcs0zNcDWHViJw2JS6sdzTldDR7Po00nxhXpDdr";
            Charge charge = Charge.create(stripeCharge.getCharge());

            addContributionPostCharge(arrayDatasSplit);

            return new ResponseEntity<String>("Success", HttpStatus.CREATED);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Failure", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void addContributionPostCharge(String[] arrayDatasSplit) {
        Long projectId = Long.parseLong(arrayDatasSplit[INDEX_PROJECT_ID]);
        contributionService.saveContributionPostCharge(Double.parseDouble(arrayDatasSplit[INDEX_AMOUNT]), projectId, arrayDatasSplit[INDEX_USERNAME]);
    }
}
