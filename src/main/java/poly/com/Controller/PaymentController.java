package poly.com.Controller;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import jakarta.servlet.http.HttpServletRequest;
import poly.com.Config.PaypalPaymentIntent;
import poly.com.Config.PaypalPaymentMethod;
import poly.com.Services.OrderService;
import poly.com.Services.UserServices;
import poly.com.utils.Utils;


@Controller
public class PaymentController {
    public static final String URL_PAYPAL_SUCCESS = "pay/success";
    public static final String URL_PAYPAL_CANCEL = "pay/cancel";
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private poly.com.Services.PaypalService paypalService;


    @Resource
    private UserServices userServices;


    @Resource
    private OrderService orderService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/pay")
    public String pay(HttpServletRequest request, @RequestParam("price") double price) {
        String cancelUrl = Utils.getBaseURL(request) + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + URL_PAYPAL_SUCCESS;
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "payment description",
                    cancelUrl,
                    successUrl);
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }

    @PostMapping("/checkout")
    public String SaveOrder() {
        try {
            int userId = getCurrentUserId();
            log.info("userId::", userId);
            orderService.save(userId);
            return "redirect:/order";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/order";
    }


    // Lấy id của người dùng hiện tại từ Authentication
    private Integer getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName(); // Lấy email từ Authentication
            return userServices.getIdUserByEmail(email); // Lấy id_user từ UserServices
        }
        return null;
    }
}
