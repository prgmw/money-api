package br.com.money.config.property;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("money")
@Getter
public class MoneyProperty {

    private final Seguranca seguranca = new Seguranca();
    private final Origem origem = new Origem();


    @Getter
    public class Seguranca {
        private boolean enableHttps;
    }

    @Getter
    public class Origem {
        private String endereco;
    }

}
