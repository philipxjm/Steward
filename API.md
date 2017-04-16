# API

## getStockData

### Parameters

| name   | description                                                      | example                    |
|--------|------------------------------------------------------------------|----------------------------|
| ticker | Ticker of stock to get data for                                  | AAPL                       |
| range  | Time range to get data for given as tuple of two unix timestamps | [ 1477448008, 1492371726 ] |

### Response
```
{
  data : List of timestamps and stock value
         [ (<Timestamp>, <Value>), ... ] 
}
```
## getPredData

### Parameters

| name   | description                           | example |
|--------|---------------------------------------|---------|
| ticker | Ticker of stock to get prediction for | AAPL    |

### Response
```
{
  pred : List of timestamps and predicted stock value
         [ (<Timestamp>, <Value>), ... ]
}
```
## getStocksForUser

### Parameters

| name | description                         | example    |
|------|-------------------------------------|------------|
| user | User to get portfolio stocks for    | wpovell    |

### Response
```
{
  stocks : List of stock tickers in user portfolio
           [ <Stock Ticker>, ... ]
}
```
