Feature: sample for API get data from mock web site


  Scenario: get entities data details
    Given send "valid" get request to get vendor registered data details
    Then response status code is "200"
