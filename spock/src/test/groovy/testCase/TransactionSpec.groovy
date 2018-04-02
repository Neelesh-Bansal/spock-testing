package testCase

import Source.*
import spock.lang.Specification

class TransactionSpec extends Specification {

    void setup(){}

    void "Testing Sell Method1"(){

        setup:
        Transaction transaction = new Transaction()
        User user1 = new User(isPrivellegedCustomer: true, balance: balance)
        Product product1 = new Product(price: price)

        when:
        transaction.sell(product1,user1)
        then:
        thrown(SaleException)

        when:
        transaction.sell(product1,user1)

        then:
        user1.purchasedProducts.size() == size
        user1.balance == value

        where:
        balance   | price   | size  | value
        2000      | 500     | 1     | 1500
        2000      | 2000    | 1     | 0
    }

    void "Testing Sell Method2"(){
        setup:
        Transaction transaction = new Transaction()
        User user2 = new User(isPrivellegedCustomer: true, balance: 1000)
        Product product2 = new Product(price: 2000)

        when:
        transaction.sell(product2,user2)

        then:
        thrown(SaleException)
    }

    def "Testing Discount Method1"(){
        setup:
        Transaction transaction = new Transaction()
        User user2 = new User(isPrivellegedCustomer: true, balance: 2000)
        Product product2 = new Product(name: "Product1",discountType: DiscountType.PRIVELLEGE_ONLY,price: 1000)

        when:
        BigDecimal result = transaction.calculateDiscount(product2,user2)

        then:
        result == 300
    }

    def "Testing Discount Method2"(){
        setup:
        Transaction transaction = new Transaction()
        User user2 = new User(isPrivellegedCustomer: false, balance: 2000)
        Product product2 = new Product(name: "Product1",discountType: DiscountType.PRIVELLEGE_ONLY,price: 1000)

        when:
        BigDecimal result = transaction.calculateDiscount(product2,user2)

        then:
        result == 100
    }


    def "Testing CancelSale Method"() {

        given:
        User user = new User(isPrivellegedCustomer: false,balance: 3500)
        Product product = new Product(name: 'Product1', price: 1000)

        Transaction transaction = new Transaction()
        user.cancelPurchase(product)

        when:
        transaction.sell(product,user)

        then:
        user.purchasedProducts.size() == 1
        user.balance == 800

        when:
        transaction.cancelSale(product, user)

        then:
        user.purchasedProducts.size() == 0
        user.balance == 1600
    }

    void "Testing getAllPopularProducts Method"() {
        setup:
        Transaction transaction = new Transaction()

        when:
        List<Product> popularProducts = transaction.getAllPopularProducts()

        then:
        popularProducts.size() == 2
    }

}