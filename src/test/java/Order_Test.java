import CommonUtil.Common;
import Order_POM.Order;
import org.testng.annotations.Test;

import java.io.IOException;

public class Order_Test extends Common {

    @Test
    public void openPage() throws IOException, InterruptedException {
        Order order =  new Order(driver);
        order.enterUser("shreyas.patil@ecuworldwide.com");
        order.enterPass("Shreyas@21test");
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