package edu.steward.stock.api;

public class TestAlphaParse {
	public static void main(String[] args) {
	String json = "{\n    \"Meta Data\": {\n        \"1. Information\": \"Intraday (15min) prices and volumes\",\n        \"2. Symbol\": \"MSFT\",\n        \"3. Last Refreshed\": \"2017-04-18 16:00:00\",\n        \"4. Interval\": \"15min\",\n        \"5. Output Size\": \"Compact\",\n        \"6. Time Zone\": \"US/Eastern\"\n    },\n    \"Time Series (15min)\": {\n        \"2017-04-18 16:00:00\": {\n            \"1. open\": \"65.3950\",\n            \"2. high\": \"65.4900\",\n            \"3. low\": \"65.3800\",\n            \"4. close\": \"65.3900\",\n            \"5. volume\": \"1988124\"\n        },\n        \"2017-04-18 15:45:00\": {\n            \"1. open\": \"65.3900\",\n            \"2. high\": \"65.4300\",\n            \"3. low\": \"65.3750\",\n            \"4. close\": \"65.3900\",\n            \"5. volume\": \"410482\"\n        },\n        \"2017-04-18 15:30:00\": {\n            \"1. open\": \"65.3500\",\n            \"2. high\": \"65.4200\",\n            \"3. low\": \"65.3400\",\n            \"4. close\": \"65.3950\",\n            \"5. volume\": \"295334\"\n        },\n        \"2017-04-18 15:15:00\": {\n            \"1. open\": \"65.3800\",\n            \"2. high\": \"65.4000\",\n            \"3. low\": \"65.3300\",\n            \"4. close\": \"65.3500\",\n            \"5. volume\": \"339930\"\n        },\n        \"2017-04-18 15:00:00\": {\n            \"1. open\": \"65.3100\",\n            \"2. high\": \"65.3800\",\n            \"3. low\": \"65.2800\",\n            \"4. close\": \"65.3766\",\n            \"5. volume\": \"391392\"\n        },\n        \"2017-04-18 14:45:00\": {\n            \"1. open\": \"65.3800\",\n            \"2. high\": \"65.4164\",\n            \"3. low\": \"65.2900\",\n            \"4. close\": \"65.3000\",\n            \"5. volume\": \"283821\"\n        },\n        \"2017-04-18 14:30:00\": {\n            \"1. open\": \"65.3613\",\n            \"2. high\": \"65.4000\",\n            \"3. low\": \"65.3300\",\n            \"4. close\": \"65.3950\",\n            \"5. volume\": \"277261\"\n        },\n        \"2017-04-18 14:15:00\": {\n            \"1. open\": \"65.3990\",\n            \"2. high\": \"65.4300\",\n            \"3. low\": \"65.3600\",\n            \"4. close\": \"65.3654\",\n            \"5. volume\": \"205205\"\n        },\n        \"2017-04-18 14:00:00\": {\n            \"1. open\": \"65.4200\",\n            \"2. high\": \"65.4300\",\n            \"3. low\": \"65.3647\",\n            \"4. close\": \"65.4000\",\n            \"5. volume\": \"158094\"\n        },\n        \"2017-04-18 13:45:00\": {\n            \"1. open\": \"65.4100\",\n            \"2. high\": \"65.4600\",\n            \"3. low\": \"65.3800\",\n            \"4. close\": \"65.4150\",\n            \"5. volume\": \"320694\"\n        },\n        \"2017-04-18 13:30:00\": {\n            \"1. open\": \"65.3800\",\n            \"2. high\": \"65.4100\",\n            \"3. low\": \"65.3300\",\n            \"4. close\": \"65.4100\",\n            \"5. volume\": \"254451\"\n        },\n        \"2017-04-18 13:15:00\": {\n            \"1. open\": \"65.3300\",\n            \"2. high\": \"65.3900\",\n            \"3. low\": \"65.2800\",\n            \"4. close\": \"65.3800\",\n            \"5. volume\": \"326942\"\n        },\n        \"2017-04-18 13:00:00\": {\n            \"1. open\": \"65.3500\",\n            \"2. high\": \"65.3900\",\n            \"3. low\": \"65.3200\",\n            \"4. close\": \"65.3301\",\n            \"5. volume\": \"280767\"\n        },\n        \"2017-04-18 12:45:00\": {\n            \"1. open\": \"65.2900\",\n            \"2. high\": \"65.4000\",\n            \"3. low\": \"65.2800\",\n            \"4. close\": \"65.3450\",\n            \"5. volume\": \"355561\"\n        },\n        \"2017-04-18 12:30:00\": {\n            \"1. open\": \"65.3050\",\n            \"2. high\": \"65.3100\",\n            \"3. low\": \"65.2600\",\n            \"4. close\": \"65.2950\",\n            \"5. volume\": \"391552\"\n        },\n        \"2017-04-18 12:15:00\": {\n            \"1. open\": \"65.2700\",\n            \"2. high\": \"65.3600\",\n            \"3. low\": \"65.2650\",\n            \"4. close\": \"65.3000\",\n            \"5. volume\": \"556531\"\n        },\n        \"2017-04-18 12:00:00\": {\n            \"1. open\": \"65.3100\",\n            \"2. high\": \"65.3800\",\n            \"3. low\": \"65.2500\",\n            \"4. close\": \"65.2800\",\n            \"5. volume\": \"594298\"\n        },\n        \"2017-04-18 11:45:00\": {\n            \"1. open\": \"65.4250\",\n            \"2. high\": \"65.4500\",\n            \"3. low\": \"65.2710\",\n            \"4. close\": \"65.3100\",\n            \"5. volume\": \"778703\"\n        },\n        \"2017-04-18 11:30:00\": {\n            \"1. open\": \"65.5100\",\n            \"2. high\": \"65.5300\",\n            \"3. low\": \"65.4200\",\n            \"4. close\": \"65.4300\",\n            \"5. volume\": \"512963\"\n        },\n        \"2017-04-18 11:15:00\": {\n            \"1. open\": \"65.4700\",\n            \"2. high\": \"65.5450\",\n            \"3. low\": \"65.4250\",\n            \"4. close\": \"65.5150\",\n            \"5. volume\": \"565506\"\n        },\n        \"2017-04-18 11:00:00\": {\n            \"1. open\": \"65.5500\",\n            \"2. high\": \"65.5500\",\n            \"3. low\": \"65.3800\",\n            \"4. close\": \"65.4611\",\n            \"5. volume\": \"541916\"\n        },\n        \"2017-04-18 10:45:00\": {\n            \"1. open\": \"65.5100\",\n            \"2. high\": \"65.5500\",\n            \"3. low\": \"65.4450\",\n            \"4. close\": \"65.5400\",\n            \"5. volume\": \"490101\"\n        },\n        \"2017-04-18 10:30:00\": {\n            \"1. open\": \"65.5500\",\n            \"2. high\": \"65.5800\",\n            \"3. low\": \"65.4700\",\n            \"4. close\": \"65.5100\",\n            \"5. volume\": \"433511\"\n        },\n        \"2017-04-18 10:15:00\": {\n            \"1. open\": \"65.6200\",\n            \"2. high\": \"65.7100\",\n            \"3. low\": \"65.5000\",\n            \"4. close\": \"65.5450\",\n            \"5. volume\": \"916082\"\n        },\n        \"2017-04-18 10:00:00\": {\n            \"1. open\": \"65.3450\",\n            \"2. high\": \"65.6600\",\n            \"3. low\": \"65.3350\",\n            \"4. close\": \"65.6250\",\n            \"5. volume\": \"1024739\"\n        },\n        \"2017-04-18 09:45:00\": {\n            \"1. open\": \"65.3200\",\n            \"2. high\": \"65.4500\",\n            \"3. low\": \"65.1600\",\n            \"4. close\": \"65.3400\",\n            \"5. volume\": \"653922\"\n        },\n        \"2017-04-18 09:30:00\": {\n            \"1. open\": \"65.3300\",\n            \"2. high\": \"65.3300\",\n            \"3. low\": \"65.3200\",\n            \"4. close\": \"65.3200\",\n            \"5. volume\": \"195782\"\n        },\n        \"2017-04-17 16:00:00\": {\n            \"1. open\": \"65.3700\",\n            \"2. high\": \"65.4900\",\n            \"3. low\": \"65.3600\",\n            \"4. close\": \"65.4800\",\n            \"5. volume\": \"3609676\"\n        },\n        \"2017-04-17 15:45:00\": {\n            \"1. open\": \"65.4600\",\n            \"2. high\": \"65.4700\",\n            \"3. low\": \"65.3600\",\n            \"4. close\": \"65.3700\",\n            \"5. volume\": \"715304\"\n        },\n        \"2017-04-17 15:30:00\": {\n            \"1. open\": \"65.3893\",\n            \"2. high\": \"65.4900\",\n            \"3. low\": \"65.3750\",\n            \"4. close\": \"65.4600\",\n            \"5. volume\": \"959687\"\n        },\n        \"2017-04-17 15:15:00\": {\n            \"1. open\": \"65.3300\",\n            \"2. high\": \"65.3900\",\n            \"3. low\": \"65.3000\",\n            \"4. close\": \"65.3900\",\n            \"5. volume\": \"310810\"\n        },\n        \"2017-04-17 15:00:00\": {\n            \"1. open\": \"65.3950\",\n            \"2. high\": \"65.4050\",\n            \"3. low\": \"65.3100\",\n            \"4. close\": \"65.3300\",\n            \"5. volume\": \"386815\"\n        },\n        \"2017-04-17 14:45:00\": {\n            \"1. open\": \"65.3650\",\n            \"2. high\": \"65.4200\",\n            \"3. low\": \"65.3500\",\n            \"4. close\": \"65.3900\",\n            \"5. volume\": \"400420\"\n        },\n        \"2017-04-17 14:30:00\": {\n            \"1. open\": \"65.3850\",\n            \"2. high\": \"65.4300\",\n            \"3. low\": \"65.3400\",\n            \"4. close\": \"65.3699\",\n            \"5. volume\": \"365830\"\n        },\n        \"2017-04-17 14:15:00\": {\n            \"1. open\": \"65.4000\",\n            \"2. high\": \"65.4200\",\n            \"3. low\": \"65.3500\",\n            \"4. close\": \"65.3800\",\n            \"5. volume\": \"226989\"\n        },\n        \"2017-04-17 14:00:00\": {\n            \"1. open\": \"65.3300\",\n            \"2. high\": \"65.4400\",\n            \"3. low\": \"65.3300\",\n            \"4. close\": \"65.4000\",\n            \"5. volume\": \"265705\"\n        },\n        \"2017-04-17 13:45:00\": {\n            \"1. open\": \"65.3950\",\n            \"2. high\": \"65.3990\",\n            \"3. low\": \"65.3300\",\n            \"4. close\": \"65.3300\",\n            \"5. volume\": \"192646\"\n        },\n        \"2017-04-17 13:30:00\": {\n            \"1. open\": \"65.3850\",\n            \"2. high\": \"65.4200\",\n            \"3. low\": \"65.3700\",\n            \"4. close\": \"65.3950\",\n            \"5. volume\": \"183514\"\n        },\n        \"2017-04-17 13:15:00\": {\n            \"1. open\": \"65.3400\",\n            \"2. high\": \"65.4200\",\n            \"3. low\": \"65.3100\",\n            \"4. close\": \"65.3800\",\n            \"5. volume\": \"178313\"\n        },\n        \"2017-04-17 13:00:00\": {\n            \"1. open\": \"65.3700\",\n            \"2. high\": \"65.4000\",\n            \"3. low\": \"65.3200\",\n            \"4. close\": \"65.3400\",\n            \"5. volume\": \"220644\"\n        },\n        \"2017-04-17 12:45:00\": {\n            \"1. open\": \"65.4200\",\n            \"2. high\": \"65.4200\",\n            \"3. low\": \"65.3200\",\n            \"4. close\": \"65.3650\",\n            \"5. volume\": \"165364\"\n        },\n        \"2017-04-17 12:30:00\": {\n            \"1. open\": \"65.4000\",\n            \"2. high\": \"65.4600\",\n            \"3. low\": \"65.3800\",\n            \"4. close\": \"65.4150\",\n            \"5. volume\": \"272918\"\n        },\n        \"2017-04-17 12:15:00\": {\n            \"1. open\": \"65.3600\",\n            \"2. high\": \"65.4300\",\n            \"3. low\": \"65.3600\",\n            \"4. close\": \"65.4000\",\n            \"5. volume\": \"258416\"\n        },\n        \"2017-04-17 12:00:00\": {\n            \"1. open\": \"65.3900\",\n            \"2. high\": \"65.4400\",\n            \"3. low\": \"65.3600\",\n            \"4. close\": \"65.3600\",\n            \"5. volume\": \"604267\"\n        },\n        \"2017-04-17 11:45:00\": {\n            \"1. open\": \"65.3600\",\n            \"2. high\": \"65.4200\",\n            \"3. low\": \"65.3333\",\n            \"4. close\": \"65.3850\",\n            \"5. volume\": \"267674\"\n        },\n        \"2017-04-17 11:30:00\": {\n            \"1. open\": \"65.4100\",\n            \"2. high\": \"65.4700\",\n            \"3. low\": \"65.3200\",\n            \"4. close\": \"65.3501\",\n            \"5. volume\": \"441396\"\n        },\n        \"2017-04-17 11:15:00\": {\n            \"1. open\": \"65.3200\",\n            \"2. high\": \"65.4100\",\n            \"3. low\": \"65.3200\",\n            \"4. close\": \"65.4100\",\n            \"5. volume\": \"452165\"\n        },\n        \"2017-04-17 11:00:00\": {\n            \"1. open\": \"65.3300\",\n            \"2. high\": \"65.3650\",\n            \"3. low\": \"65.3000\",\n            \"4. close\": \"65.3250\",\n            \"5. volume\": \"360499\"\n        },\n        \"2017-04-17 10:45:00\": {\n            \"1. open\": \"65.3600\",\n            \"2. high\": \"65.4000\",\n            \"3. low\": \"65.2900\",\n            \"4. close\": \"65.3300\",\n            \"5. volume\": \"309831\"\n        },\n        \"2017-04-17 10:30:00\": {\n            \"1. open\": \"65.3700\",\n            \"2. high\": \"65.4500\",\n            \"3. low\": \"65.3200\",\n            \"4. close\": \"65.3600\",\n            \"5. volume\": \"500239\"\n        },\n        \"2017-04-17 10:15:00\": {\n            \"1. open\": \"65.2700\",\n            \"2. high\": \"65.3700\",\n            \"3. low\": \"65.2000\",\n            \"4. close\": \"65.3700\",\n            \"5. volume\": \"314479\"\n        },\n        \"2017-04-17 10:00:00\": {\n            \"1. open\": \"65.3100\",\n            \"2. high\": \"65.3200\",\n            \"3. low\": \"65.1400\",\n            \"4. close\": \"65.2700\",\n            \"5. volume\": \"437692\"\n        },\n        \"2017-04-17 09:45:00\": {\n            \"1. open\": \"65.0900\",\n            \"2. high\": \"65.4000\",\n            \"3. low\": \"65.0100\",\n            \"4. close\": \"65.3070\",\n            \"5. volume\": \"812659\"\n        },\n        \"2017-04-17 09:30:00\": {\n            \"1. open\": \"65.0400\",\n            \"2. high\": \"65.1000\",\n            \"3. low\": \"65.0200\",\n            \"4. close\": \"65.0900\",\n            \"5. volume\": \"285525\"\n        },\n        \"2017-04-13 16:00:00\": {\n            \"1. open\": \"65.0850\",\n            \"2. high\": \"65.2400\",\n            \"3. low\": \"64.9500\",\n            \"4. close\": \"64.9500\",\n            \"5. volume\": \"3768400\"\n        },\n        \"2017-04-13 15:45:00\": {\n            \"1. open\": \"65.2050\",\n            \"2. high\": \"65.2200\",\n            \"3. low\": \"65.0315\",\n            \"4. close\": \"65.0800\",\n            \"5. volume\": \"681358\"\n        },\n        \"2017-04-13 15:30:00\": {\n            \"1. open\": \"65.2100\",\n            \"2. high\": \"65.2701\",\n            \"3. low\": \"65.1300\",\n            \"4. close\": \"65.2052\",\n            \"5. volume\": \"393582\"\n        },\n        \"2017-04-13 15:15:00\": {\n            \"1. open\": \"65.2600\",\n            \"2. high\": \"65.3000\",\n            \"3. low\": \"65.2000\",\n            \"4. close\": \"65.2200\",\n            \"5. volume\": \"411011\"\n        },\n        \"2017-04-13 15:00:00\": {\n            \"1. open\": \"65.2400\",\n            \"2. high\": \"65.2800\",\n            \"3. low\": \"65.1900\",\n            \"4. close\": \"65.2650\",\n            \"5. volume\": \"292208\"\n        },\n        \"2017-04-13 14:45:00\": {\n            \"1. open\": \"65.2700\",\n            \"2. high\": \"65.3150\",\n            \"3. low\": \"65.2200\",\n            \"4. close\": \"65.2400\",\n            \"5. volume\": \"347819\"\n        },\n        \"2017-04-13 14:30:00\": {\n            \"1. open\": \"65.2400\",\n            \"2. high\": \"65.3200\",\n            \"3. low\": \"65.1900\",\n            \"4. close\": \"65.2800\",\n            \"5. volume\": \"386425\"\n        },\n        \"2017-04-13 14:15:00\": {\n            \"1. open\": \"65.1800\",\n            \"2. high\": \"65.3000\",\n            \"3. low\": \"65.1700\",\n            \"4. close\": \"65.2300\",\n            \"5. volume\": \"346054\"\n        },\n        \"2017-04-13 14:00:00\": {\n            \"1. open\": \"65.2600\",\n            \"2. high\": \"65.2600\",\n            \"3. low\": \"65.1500\",\n            \"4. close\": \"65.1800\",\n            \"5. volume\": \"296502\"\n        },\n        \"2017-04-13 13:45:00\": {\n            \"1. open\": \"65.2500\",\n            \"2. high\": \"65.3300\",\n            \"3. low\": \"65.2450\",\n            \"4. close\": \"65.2600\",\n            \"5. volume\": \"326112\"\n        },\n        \"2017-04-13 13:30:00\": {\n            \"1. open\": \"65.1900\",\n            \"2. high\": \"65.2993\",\n            \"3. low\": \"65.1900\",\n            \"4. close\": \"65.2550\",\n            \"5. volume\": \"254389\"\n        },\n        \"2017-04-13 13:15:00\": {\n            \"1. open\": \"65.3050\",\n            \"2. high\": \"65.3150\",\n            \"3. low\": \"65.1400\",\n            \"4. close\": \"65.1900\",\n            \"5. volume\": \"638378\"\n        },\n        \"2017-04-13 13:00:00\": {\n            \"1. open\": \"65.4100\",\n            \"2. high\": \"65.4100\",\n            \"3. low\": \"65.2650\",\n            \"4. close\": \"65.3051\",\n            \"5. volume\": \"473954\"\n        },\n        \"2017-04-13 12:45:00\": {\n            \"1. open\": \"65.4793\",\n            \"2. high\": \"65.5000\",\n            \"3. low\": \"65.3700\",\n            \"4. close\": \"65.4100\",\n            \"5. volume\": \"284632\"\n        },\n        \"2017-04-13 12:30:00\": {\n            \"1. open\": \"65.4450\",\n            \"2. high\": \"65.4900\",\n            \"3. low\": \"65.4300\",\n            \"4. close\": \"65.4800\",\n            \"5. volume\": \"298637\"\n        },\n        \"2017-04-13 12:15:00\": {\n            \"1. open\": \"65.5600\",\n            \"2. high\": \"65.5650\",\n            \"3. low\": \"65.4100\",\n            \"4. close\": \"65.4500\",\n            \"5. volume\": \"492717\"\n        },\n        \"2017-04-13 12:00:00\": {\n            \"1. open\": \"65.5650\",\n            \"2. high\": \"65.6200\",\n            \"3. low\": \"65.5400\",\n            \"4. close\": \"65.5600\",\n            \"5. volume\": \"263830\"\n        },\n        \"2017-04-13 11:45:00\": {\n            \"1. open\": \"65.6100\",\n            \"2. high\": \"65.6600\",\n            \"3. low\": \"65.5600\",\n            \"4. close\": \"65.5600\",\n            \"5. volume\": \"263871\"\n        },\n        \"2017-04-13 11:30:00\": {\n            \"1. open\": \"65.6450\",\n            \"2. high\": \"65.6600\",\n            \"3. low\": \"65.5990\",\n            \"4. close\": \"65.6100\",\n            \"5. volume\": \"427614\"\n        },\n        \"2017-04-13 11:15:00\": {\n            \"1. open\": \"65.8300\",\n            \"2. high\": \"65.8500\",\n            \"3. low\": \"65.6300\",\n            \"4. close\": \"65.6416\",\n            \"5. volume\": \"416332\"\n        },\n        \"2017-04-13 11:00:00\": {\n            \"1. open\": \"65.7550\",\n            \"2. high\": \"65.8600\",\n            \"3. low\": \"65.7200\",\n            \"4. close\": \"65.8300\",\n            \"5. volume\": \"394908\"\n        },\n        \"2017-04-13 10:45:00\": {\n            \"1. open\": \"65.7000\",\n            \"2. high\": \"65.8300\",\n            \"3. low\": \"65.6900\",\n            \"4. close\": \"65.7500\",\n            \"5. volume\": \"900164\"\n        },\n        \"2017-04-13 10:30:00\": {\n            \"1. open\": \"65.6250\",\n            \"2. high\": \"65.7200\",\n            \"3. low\": \"65.6100\",\n            \"4. close\": \"65.7150\",\n            \"5. volume\": \"477259\"\n        },\n        \"2017-04-13 10:15:00\": {\n            \"1. open\": \"65.5900\",\n            \"2. high\": \"65.7100\",\n            \"3. low\": \"65.5900\",\n            \"4. close\": \"65.6200\",\n            \"5. volume\": \"472368\"\n        },\n        \"2017-04-13 10:00:00\": {\n            \"1. open\": \"65.3800\",\n            \"2. high\": \"65.6600\",\n            \"3. low\": \"65.3400\",\n            \"4. close\": \"65.6000\",\n            \"5. volume\": \"903295\"\n        },\n        \"2017-04-13 09:45:00\": {\n            \"1. open\": \"65.3000\",\n            \"2. high\": \"65.3900\",\n            \"3. low\": \"64.9800\",\n            \"4. close\": \"65.3900\",\n            \"5. volume\": \"986715\"\n        },\n        \"2017-04-13 09:30:00\": {\n            \"1. open\": \"65.2900\",\n            \"2. high\": \"65.3100\",\n            \"3. low\": \"65.2900\",\n            \"4. close\": \"65.2900\",\n            \"5. volume\": \"471698\"\n        },\n        \"2017-04-12 16:00:00\": {\n            \"1. open\": \"65.2000\",\n            \"2. high\": \"65.2900\",\n            \"3. low\": \"65.1800\",\n            \"4. close\": \"65.2300\",\n            \"5. volume\": \"2721967\"\n        },\n        \"2017-04-12 15:45:00\": {\n            \"1. open\": \"65.2500\",\n            \"2. high\": \"65.3000\",\n            \"3. low\": \"65.1850\",\n            \"4. close\": \"65.2100\",\n            \"5. volume\": \"545408\"\n        },\n        \"2017-04-12 15:30:00\": {\n            \"1. open\": \"65.2300\",\n            \"2. high\": \"65.3200\",\n            \"3. low\": \"65.1700\",\n            \"4. close\": \"65.2500\",\n            \"5. volume\": \"729230\"\n        },\n        \"2017-04-12 15:15:00\": {\n            \"1. open\": \"65.2000\",\n            \"2. high\": \"65.2740\",\n            \"3. low\": \"65.1700\",\n            \"4. close\": \"65.2400\",\n            \"5. volume\": \"465023\"\n        },\n        \"2017-04-12 15:00:00\": {\n            \"1. open\": \"65.1200\",\n            \"2. high\": \"65.2300\",\n            \"3. low\": \"65.1100\",\n            \"4. close\": \"65.2000\",\n            \"5. volume\": \"644516\"\n        },\n        \"2017-04-12 14:45:00\": {\n            \"1. open\": \"65.2600\",\n            \"2. high\": \"65.3300\",\n            \"3. low\": \"65.1200\",\n            \"4. close\": \"65.1200\",\n            \"5. volume\": \"621625\"\n        },\n        \"2017-04-12 14:30:00\": {\n            \"1. open\": \"65.2900\",\n            \"2. high\": \"65.2950\",\n            \"3. low\": \"65.1700\",\n            \"4. close\": \"65.2600\",\n            \"5. volume\": \"519353\"\n        },\n        \"2017-04-12 14:15:00\": {\n            \"1. open\": \"65.3500\",\n            \"2. high\": \"65.3700\",\n            \"3. low\": \"65.2900\",\n            \"4. close\": \"65.2950\",\n            \"5. volume\": \"383984\"\n        },\n        \"2017-04-12 14:00:00\": {\n            \"1. open\": \"65.2500\",\n            \"2. high\": \"65.3600\",\n            \"3. low\": \"65.2046\",\n            \"4. close\": \"65.3500\",\n            \"5. volume\": \"479035\"\n        },\n        \"2017-04-12 13:45:00\": {\n            \"1. open\": \"65.1850\",\n            \"2. high\": \"65.2700\",\n            \"3. low\": \"65.1500\",\n            \"4. close\": \"65.2500\",\n            \"5. volume\": \"484070\"\n        },\n        \"2017-04-12 13:30:00\": {\n            \"1. open\": \"65.2200\",\n            \"2. high\": \"65.2700\",\n            \"3. low\": \"65.1700\",\n            \"4. close\": \"65.1900\",\n            \"5. volume\": \"301109\"\n        },\n        \"2017-04-12 13:15:00\": {\n            \"1. open\": \"65.1800\",\n            \"2. high\": \"65.3050\",\n            \"3. low\": \"65.1800\",\n            \"4. close\": \"65.2200\",\n            \"5. volume\": \"352613\"\n        },\n        \"2017-04-12 13:00:00\": {\n            \"1. open\": \"65.2650\",\n            \"2. high\": \"65.2700\",\n            \"3. low\": \"65.1300\",\n            \"4. close\": \"65.1800\",\n            \"5. volume\": \"374587\"\n        },\n        \"2017-04-12 12:45:00\": {\n            \"1. open\": \"65.2700\",\n            \"2. high\": \"65.3100\",\n            \"3. low\": \"65.2500\",\n            \"4. close\": \"65.2650\",\n            \"5. volume\": \"366318\"\n        },\n        \"2017-04-12 12:30:00\": {\n            \"1. open\": \"65.2700\",\n            \"2. high\": \"65.3000\",\n            \"3. low\": \"65.2300\",\n            \"4. close\": \"65.2800\",\n            \"5. volume\": \"252200\"\n        },\n        \"2017-04-12 12:15:00\": {\n            \"1. open\": \"65.1800\",\n            \"2. high\": \"65.3000\",\n            \"3. low\": \"65.1500\",\n            \"4. close\": \"65.2700\",\n            \"5. volume\": \"457259\"\n        },\n        \"2017-04-12 12:00:00\": {\n            \"1. open\": \"65.1800\",\n            \"2. high\": \"65.2000\",\n            \"3. low\": \"65.1150\",\n            \"4. close\": \"65.1800\",\n            \"5. volume\": \"477490\"\n        },\n        \"2017-04-12 11:45:00\": {\n            \"1. open\": \"65.3000\",\n            \"2. high\": \"65.3200\",\n            \"3. low\": \"65.1150\",\n            \"4. close\": \"65.1810\",\n            \"5. volume\": \"623098\"\n        },\n        \"2017-04-12 11:30:00\": {\n            \"1. open\": \"65.3100\",\n            \"2. high\": \"65.3400\",\n            \"3. low\": \"65.2500\",\n            \"4. close\": \"65.3000\",\n            \"5. volume\": \"368725\"\n        }\n    }\n}"; 
	AlphaVantageAPI a = new AlphaVantageAPI();
//	System.out.println(a.parseAlphaVantage(json));
	}
}
