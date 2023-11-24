# ghco-intraday-pnl

## Task: 
Develop an application that processes simplified trade data from a CSV file to calculate and display intraday cash position aggregations (PnL) in USD.

## Key Features:
Data Reading: Your application should read trade booking information from a provided CSV file.
<br>Aggregation: Calculate the cash positions (Profit and Loss, PnL) in USD. These should be aggregated based on several criteria:

BBGCode (Bloomberg Code)
<br>Portfolio
<br>Strategy
<br>User
<br>Currency

Note: Trades with the same BBGCode may occur in different currencies. Convert all amounts to USD using static conversion rates for this purpose.

Handling Trade Types: Your program must efficiently process new trades and handle amendments and cancellations.
<br>New Trades: Trades that haven’t appeared before.
<br>Amendments: Updates to previous trades (identified by matching trade IDs). Only include the most recent amendment.
<br>Cancellations: Removal of a trade (new or amended). Exclude cancelled trades from calculations.

In your calculations, account for the ‘side’ of the trade (buy or sell), as this affects whether cash is spent or received.
<br>Interactive Interface: Create an interface ( CLI is fine ) to manually add new trades and cancel existing ones, in addition to those loaded from the CSV file.

## Example: 
Consider this sample data and the resultant PnL calculation:
<br>BBGCode, time, side, quantity, price
<br>ABC1, T0, B, 100, 100
<br>ABC2, T1, B, 50, 200
<br>ABC2, T2, B, 50, 202
<br>ABC2, T3, S, 100, 205
<br>ABC1, T3, S, 50, 99

Our PnL is:
<br>ABC1 = 50*99 [Partial Sale] - 100*100 [Purchase] = -50
<br>ABC2 = 100*205 [Full Sale] - (50*200 + 50*202) [Purchase] = 400
<br>TOTAL = 400 - 50 = 350 PROFIT
