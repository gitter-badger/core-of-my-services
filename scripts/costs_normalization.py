import pymongo
import re

connection = pymongo.MongoClient("URL")
db = connection.dataservices
ads = db.ads

# is not number matcher
TEMP_DOLLAR_RATE = 15000

prog = re.compile("^[\D]+$")


def todollar(value):
    return value / TEMP_DOLLAR_RATE


try:
    query = {}
    cursor = ads.find(query)
    for ad in cursor:
        cost = ad['cost']
        print (ad['cost'])
        print(prog.match(cost) )
        costValue = 0

        if prog.match(cost) is not None:
            costValue = 99999999
        else:
            m = re.search("[\d ]+", cost)
            costValue = int(''.join([ch for ch in m.group(0) if ch.isdigit()]))
            if costValue > 1000000:
                costValue = todollar(costValue)

        print(costValue)

        # update costValue

        if 'costValue' not in ad:
            try:
                ads.update({"_id": ad["_id"]}, {"$set": {"costValue": costValue}})
            except Exception as e:
                print("No update", type(e), e)

        print("\n")

except Exception as e:
    print ("Unexpected error:", type(e), e)