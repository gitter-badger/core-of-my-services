
import pymongo
import sys

# establish a connection to the database
# connection = pymongo.MongoClient("mongodb://localhost")

connection =  pymongo.MongoClient("mongodb://nester:nester42@ds059661.mongolab.com:59661/dataservices")

db=connection.dataservices
images = db.images
ads = db.ads

def remove_image(_id):

    # get a handle to the school database
    db=connection.dataservices
    images = db.images

    try:
        result = images.delete_many({'_id':_id})
        #print ("num removed: ", result.deleted_count)

    except Exception as e:
        print ("Exception: ", type(e), e)


def deleteimages():

    print ("run removing")
    try:
        query = {}
        cursor = images.find(query)

        for image in cursor:
            did = str(image["_id"])
            cnt = ads.find({"images":did}).count()

            if cnt==0:
                print("remove ", image["_id"] )
                remove_image(image["_id"])


    except Exception as e:
        print ("Unexpected error:", type(e), e)

#55c53cc6328aebe20cfdd1ab
deleteimages()




	