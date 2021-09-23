import CommonUtil.Common;
import Order_POM.Order;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;

public class Order_Test extends Common {

    @Test
    @Parameters({"email" , "password"})
    public void openPage(String email, String password) throws IOException, InterruptedException {
        Order order =  new Order(driver);
        order.enterUser(email);
        order.enterPass(password);
        order.login();
        order.selectInstant();
        order.verifyMandatory();
        order.setSource("INNSA ");
        order.setDestination("BEANR ");
        order.setWeightVolume(10.23,6.29);
        order.setDimensions(3,4.2,5.1,10,2.5);
        order.getQuote();
        order.saveQuote();
        order.viewDetails();
        order.verifyDetailsPage();
        order.bookNow();
        order.logout();
    }
}