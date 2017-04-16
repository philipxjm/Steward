# API

1. [getStockData](#getStockData)
2. [getStockEssentials](#getStockEssentials)
3. [getPredData](#getPredData)
4. [getStocksForUser](#getStocksForUser)
5. [addStockForUser](#addStockForUser)

<span id="getStockData"></span>
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

<span id="getStockEssentials"></span>
## getStockEssentials

### Parameters

| name   | description                           | example |
|--------|---------------------------------------|---------|
| ticker | Ticker of stock to get essentials for | AAPL    |

### Response
```
{
  range   : 
  open    : 
  mkt_cap :
  div     :
  yield   :
  shares  :
  inst    :
  52_wk   :
  vol     :
  avg     :
  pe      :
  eps     :
  beta    :
}
```

<span id="getPredData"></span>
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

<span id="getStocksForUser"></span>
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

<span id="addStockForUser"></span>
## addStockForUser

### Parameters

| name   | description                    | example    |
|--------|--------------------------------|------------|
| user   | User to add stock to portfolio | wpovell    |
| ticker | Ticker of stock to add         | AAPL       |

### Response
```
{ }
```