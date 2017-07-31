import com.mercadopago.SDK;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.Item;
import com.mercadopago.resources.datastructures.Payer;

import static com.mercadopago.resources.datastructures.payment.Order.Type.mercadopago;

class main {

    public static void main(String [ ] args) {

        try {

            mercadopago.SDK.setClientId("6295877106812064");
            mercadopago.SDK.setClientSecret("N8h64ko1SbY2ucyZVmOMyBJN1B82ajZp");

            Preferences preference = new Preferences();



            Item item = new Item();
            Payer payer = new Payer();

            item.setId("1234")
                .setUnitPrice((float) 14.5)
                .setQuantity(2)
                .setCategoryId("ARS");

            //preference.setAutoReturn(all);

            payer.setEmail("demo@mail.com")
                 .setName("Juan");

            preference.setPayer(payer);
            preference.appendItem(item);

            System.out.println(preference.create());
        } catch (MPException e) {
            e.printStackTrace();
        }


    }

}