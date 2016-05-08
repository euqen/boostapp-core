package jav.payments;

import org.junit.Before;
import org.junit.Test;
import resources.payments.Payment;
import resources.payments.PaymentActions;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestPayment {

    private Payment payment;

    private Integer userId = 12;
    private String email = "e.shilin@paralect.com";
    private Date createdOn = new Date(2016, 9, 1);
    private int projectId = 11;
    private int amountInCents = 50;
    private PaymentActions paymentActions = new PaymentActions();

    @Before
    public void setUp() throws Exception {
        payment = new Payment();
        payment.setProjectId(projectId);
        payment.setCreatedOn(createdOn);
        payment.setUserId(userId);
        payment.setAmountInCents(amountInCents);
    }

    @Test
    public void getId() throws Exception {
        assertNotNull(payment.getId());
    }

    @Test
    public void getProjectId() throws Exception {
        assertEquals(payment.getProjectId(), projectId);
    }

    @Test
    public void getCreatedOn() throws Exception {
        assertEquals(payment.getCreatedOn(), createdOn);
    }

    @Test
    public void getUserId() throws Exception {
        assertEquals(payment.getUserId(), userId);
    }

    @Test
    public void getAmountInCents() throws Exception {
        assertEquals(payment.getAmountInCents(), amountInCents);
    }

    @Test
    public void testPaymentWithUserNotLoggedIn() throws Exception {
        paymentActions.create();
        assertEquals(paymentActions.getValidateErrors().get(0).get("user"), "You are not logged in");
    }

    @Test
    public void testPaymentWithoutData() throws Exception {
        paymentActions.setEmail(this.email);
        paymentActions.create();

        assertEquals(paymentActions.getValidateErrors().get(0).get("all"), "Please select correct project");
    }

    @Test
    public void testPaymentWithoutUserId() throws Exception {
        paymentActions.setEmail(this.email);
        paymentActions.setProjectId(12);
        paymentActions.create();

        assertEquals(paymentActions.getValidateErrors().get(0).get("all"), "Please select correct project");
    }

    @Test
    public void testPaymentWithoutProjectId() throws Exception {
        paymentActions.setEmail(this.email);
        paymentActions.setUserId(234);
        paymentActions.create();

        assertEquals(paymentActions.getValidateErrors().get(0).get("all"), "Please select correct project");
    }

    @Test
    public void testPaymentWithIncorrectAmount() throws Exception {
        paymentActions.setEmail(this.email);
        paymentActions.setUserId(234);
        paymentActions.setProjectId(12);
        paymentActions.setAmountInCents(-9999);
        paymentActions.create();

        assertEquals(paymentActions.getValidateErrors().get(0).get("amount"), "Payment amount could not be less or equals zero");
    }

    @Test
    public void testPaymentWithIncorrectAmount1() throws Exception {
        paymentActions.setEmail(this.email);
        paymentActions.setUserId(234);
        paymentActions.setProjectId(12);
        paymentActions.setAmountInCents(0);
        paymentActions.create();

        assertEquals(paymentActions.getValidateErrors().get(0).get("amount"), "Payment amount could not be less or equals zero");
    }

    @Test
    public void testPaymentWithIncorrectAmount2() throws Exception {
        paymentActions.setEmail(this.email);
        paymentActions.setUserId(234);
        paymentActions.setProjectId(12);
        paymentActions.setAmountInCents(999000000);
        paymentActions.create();

        assertEquals(paymentActions.getValidateErrors().get(0).get("amount"), "Payment amount could not be more than 5000$");
    }

    @Test
    public void testPaymentWithIncorrectAmount3() throws Exception {
        paymentActions.setEmail(this.email);
        paymentActions.setUserId(234);
        paymentActions.setProjectId(12);
        paymentActions.setAmountInCents(500001);
        paymentActions.create();

        assertEquals(paymentActions.getValidateErrors().get(0).get("amount"), "Payment amount could not be more than 5000$");
    }

    @Test
    public void testPaymentWithIncorrectProject() throws Exception {
        paymentActions.setEmail(this.email);
        paymentActions.setUserId(234);
        paymentActions.setProjectId(-12);
        paymentActions.setAmountInCents(3000);
        paymentActions.create();

        assertEquals(paymentActions.getValidateErrors().get(0).get("project"), "This project does not exists");
    }

    @Test
    public void testPaymentWithUserNotLogged() throws Exception {
        paymentActions.create();
        assertEquals(paymentActions.getValidateErrors().get(0).get("user"), "You are not logged in");
    }
}
