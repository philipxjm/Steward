# API

1. [getStockData](#getStockData)
2. [getPredData](#getPredData)
3. [getStocksForUser](#getStocksForUser)

## getStockData <span id="getStockData"></span>

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

## getPredData <span id="getPredData"></span>

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

## getStocksForUser <span id="getStocksForUser"></span>

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
