package testCase

import Source.PasswordEncrypterService
import spock.lang.Specification

class PasswordEncrypterServiceTest extends Specification {

    def "Testing Encrypt Method"(){
        setup:
        String password = pass

        when:
        PasswordEncrypterService encryptIt = new PasswordEncrypterService()
        String encryptedPassword = encryptIt.encrypt(password)

        then:
        String decodedPassword = new String(encryptedPassword.decodeBase64())
        password2==decodedPassword

        where:
        pass         |  password2
        "Hello"      |  "Hello"
        "123%12@"    |  "123%12@"
        "jbe-123*^%" |  "jbe-123*^%"

         
    }
}

