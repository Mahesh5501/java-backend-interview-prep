package DesignPatterns.adapter.external;

// Imagine this class comes from the Razorpay SDK.
// We cannot change its API to match our application.
public class RazorpayClient {

    public void createPayment(long amountInPaise) {

        System.out.println(
                "Razorpay processing payment: "
                        + amountInPaise + " paise"
        );
    }
}
