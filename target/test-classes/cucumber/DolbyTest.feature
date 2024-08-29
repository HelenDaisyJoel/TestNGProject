@tag
Feature: WebAutomation-CucumberBDD-PrismWeb
  I want to use this template for my feature file

  Background: 
    Given Landed in 1ST Application

  @tag2
  Scenario Outline: Dolby Booking workflow - VideoEncoderTest
    #	Given Landed in 1ST Application
    Given Create Encoder with DisplayName "<DisplayName>" and ID "<EncoderID>"
    Then close the application

    Examples: 
      | DisplayName | EncoderID |
      | Test-01     | Test-01   |

  #@tag3
  #Scenario Outline: Dolby Booking workflow - VideoDecoderTest
  #Given Create Decoder with DisplayName "<DolbyDisplayName>" and ID "<DecoderID>"
  #Then close the application
  #
  #Examples:
  #| DolbyDisplayName | DecoderID   |
  #| DolbyDecoder-10  | DolbyID-10  |
  @tag4
  Scenario Outline: Dolby Booking workflow - VideoSourceTest
    Given Create Source with "<sourceName>" "<sourceAngle>" "<ForeignIDType>" "<ForeignIDValue>" "<AutomatedStartMargin>" "<AutomatedEndMargin>" "<AutomateBooking>"
    Then close the application

    Examples: 
      | sourceName | sourceAngle | ForeignIDType   | ForeignIDValue | AutomatedStartMargin | AutomatedEndMargin | AutomateBooking |
      | Source-01  | Backside    | CHRIMSEventCode | test           | 10s                  | 10s                | true            |

  @tag5
  Scenario Outline: Dolby Booking workflow - VideoChannelTest
    Given Create Channel with "<DisplayName>" "<AutoAngle>" "<connectorID>" "<clusterName>" "<height>" "<frameRate>" "<description>" "<ForeignIDType>" "<ForeignIDValue>" "<createReplay>"
    Then close the application

    Examples: 
      | DisplayName   | AutoAngle | connectorID | clusterName | height | frameRate | description | ForeignIDType | ForeignIDValue | createReplay |
      | TestChannel01 | true      |         123 | Amsterdam   |   1080 |        30 | TestDesc    | Type1         | Value1         | true         |

  @tag6
  Scenario Outline: Dolby Booking workflow - VideoBookingTest
    Given Create Booking with "<StartTime>" "<EndTime>" "<ForeignIDType>" "<ForeignIDValue>"
    Then close the application

    Examples: 
      | StartTime | EndTime | ForeignIDType   | ForeignIDValue |
      | 10:00     | 11:00   | CHRIMSEventCode | test           |
