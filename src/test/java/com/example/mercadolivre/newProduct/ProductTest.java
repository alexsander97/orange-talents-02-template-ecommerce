package com.example.mercadolivre.newProduct;

import com.example.mercadolivre.newCategory.Category;
import com.example.mercadolivre.newUser.CleanPassword;
import com.example.mercadolivre.newUser.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProductTest {


    @DisplayName("Deve validar se é possivel tirar do estoque")
    @ParameterizedTest
    @CsvSource({"0", "-1", "-100"})
    void teste(int quantity) {
        List<NewCharacteristicRequest> characteristics = Arrays.asList(new NewCharacteristicRequest("Um nome", "Uma descrição"),
                new NewCharacteristicRequest("Outro nome", "Outra descrição"),
                new NewCharacteristicRequest("Mais um nome", "Mais uma descrição"));
        Category category = new Category("categoria", null);
        User user = new User("email@email.com", new CleanPassword("123456"));
        Product product = new Product("nome", new BigDecimal(10), 10, characteristics, "test", category, user);

        assertThrows(IllegalArgumentException.class, () -> {
            product.withdrawStock(quantity);
        });
    }

    @DisplayName("Deve validar se é possivel tirar do estoque/2")
    @ParameterizedTest
    @CsvSource({"1,1,true", "1,2,false", "4,2,true", "1,5,false"})
    void teste2(int stock, int quantityRequested, boolean expectedResult) {
        List<NewCharacteristicRequest> characteristics = Arrays.asList(new NewCharacteristicRequest("Um nome", "Uma descrição"),
                new NewCharacteristicRequest("Outro nome", "Outra descrição"),
                new NewCharacteristicRequest("Mais um nome", "Mais uma descrição"));
        Category category = new Category("categoria", null);
        User user = new User("email@email.com", new CleanPassword("123456"));
        Product product = new Product("nome", new BigDecimal(10), stock, characteristics, "test", category, user);

        boolean result = product.withdrawStock(quantityRequested);

        assertEquals(expectedResult, result);
    }


}
