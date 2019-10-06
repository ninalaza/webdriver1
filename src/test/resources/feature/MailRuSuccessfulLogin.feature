Feature: MailRu login

  Background:
    Given I open main MailRu page

  Scenario Outline: Login to Mail Ru
    When I enter <login> and <password>
    And I press to enter button
    Then Successful login is performed
    Examples:
      | login               | password  |
      | nina_lazar_at_test1 | psw_test  |
      | nina_lazar_at_test2 | psw_test2 |

