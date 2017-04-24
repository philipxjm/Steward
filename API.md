# API

1. [getStockData](#getStockData)
2. [getStockEssentials](#getStockEssentials)
3. [getPredData](#getPredData)
4. [getStocksForUser](#getStocksForUser)
5. [addStockForUser](#addStockForUser)

<span portfolioId="getStockData"></span>
## getStockData

### Parameters

| name   | description                                           | example    |
|--------|-------------------------------------------------------|------------|
| ticker | Ticker of stock to get data for                       | AAPL       |
| start  | Start of time range to get data for as unix timestamp | 1477448008 |
| end    | End of time range to get data for as unix timestamp   | 1492371726 |

### Response
```
{
  data : List of timestamps and stock value
         [ (<Timestamp>, <Value>), ... ] 
}
```

<span portfolioId="getStockEssentials"></span>
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

<span portfolioId="getPredData"></span>
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

<span portfolioId="getStocksForUser"></span>
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

<span portfolioId="addStockForUser"></span>
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
