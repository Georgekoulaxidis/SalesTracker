# SalesTracker

Filters to use:
  AvailableTo
  BestOfferOnly (secret technique when things get hard)
  Currency
  Condition
  FeedbackScoreMax
  FreeShippingOnly
  LocalPickupOnly
  MaxDistance
  PaymentMethod
  Seller
  TopRatedSellerOnly (RadioButton)
  MaxPrice
  MinPrice

Hot filters:
  AvailableTo (plaintext that the user will insert the country and we will have find the country's code)
  Condition (RadioButton)
  FreeShippingOnly (RadioButton, if it's marked print a button to the next activity for statistic metrics)
  PaymentMethod (RadioButton)
  MaxPrice-MinPrice (Price range)
  Currency (List with all currencies that the user can pick)

How to compare products: 
  We take the best offer from one day and
  compare it with the new best offer that our app found and change it
  if it's better.

We use Keywords to search for products.

DB: table1: userid, username, password
	table2:userid, productIdentifier, productId, price

First goal: A small window with a plaintext and a button, that
searches for the best offers for a product that user has insert.
Details: Request to E-bay api for a product, compare based on 
the price and print the results.

GitKraken actions: Commit, pull, push

We use Gson library to parse json data.
