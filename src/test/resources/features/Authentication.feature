Feature: Login and authentication scenarios fo Vendors gate

  Scenario: login as a vendor to AWQAFF gateway
    Given login as "Vendor" "One"
    Then response status code is "200"