Feature: Automation Assessment Pepperfry.com
  Scenario: To verify you pay value on cart and address are same
    Given user browse to pepperfry live website and click on profile
    When user login to site using email and password
    And user search ‘sofa’ which redirects to product list
    And user clicks very first product from the list and product detail page gets open in new tab
    And user selects 2 in the quantity dropdown and clicks on ‘Buy Now’ button
    And on cart page apply coupon ‘Republic’ and store the ‘You Pay’ values
    And user click on Place Order button, it will redirect to address page
    Then store the You Pay value on address
    And compare those with earlier stored You Pay value of cart

  Scenario: To verify entering valid data address gets added in address book
    Given user browse to pepperfry live website and click on profile
    When user login to site using email and password
    And hover on profile and click on ‘My Account’
    Then click on ‘Add New Address’ and pop will be displayed
    And enter all the valid details and save the address

  Scenario: To verify products getting added to wishlist
    Given user browse to pepperfry live website and click on profile
    When user login to site using email and password
    And hover on ‘Furniture’ and click on three Seater Sofa
    And on redirected product listing page, click on first product
    And it will open product in new tab, click on ‘Add to wishlist’ and store the product name
    And click on previous tab, click on second product from the list
    And it will open product in new tab, click on ‘Add to wishlist’ and store the product name
    Then click on Wishlist on the top section
    And Validate the products in the side panel
    And Validate the count of the Wishlist and the number of products listed in the side panell







