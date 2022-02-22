import argparse
from cloudant.client import CouchDB
from cloudant.client import Cloudant
from cloudant.error import CloudantException
from cloudant.result import Result, ResultByKey
from dotenv import load_dotenv
from pathlib import Path
import os

#https://github.com/joke2k/faker/blob/master/LICENSE.txt
from faker import Faker

from ibmcloudant.cloudant_v1 import CloudantV1
from ibm_cloud_sdk_core.authenticators import BasicAuthenticator
import logging
import random
import sys
import time


logging.basicConfig(format='%(asctime)s %(message)s', level=logging.INFO)

parser = argparse.ArgumentParser()
parser.add_argument('-total_attorneys', type=int, default=5, help='Number of new attorneys to add to the database')
parser.add_argument('-total_clients', type=int, default=5, help='Number of new clients to add to the database')
parser.add_argument('-total_cases', type=int, default=5, help='Number of new cases to add to the database')
parser.add_argument('-env_file', type=str, default='../src/main/liberty/config/server.env', help='Path to env file')

args = parser.parse_args()
total_attorneys = args.total_attorneys
total_clients = args.total_clients
total_cases = args.total_cases
env_file = args.env_file

if os.path.exists(env_file):
    dotenv_path = Path(env_file)
    load_dotenv(dotenv_path=dotenv_path)
else:
    logging.error('Path to environment file does not exist')
    sys.exit(1)


fake = Faker()


def getAttorney(id=None):
    dict = {}
    if id is not None:
        dict['_id'] = id
    
    dict['username'] = fake.name()    
    return dict 


def getClient(id=None):    
    dict = {}
    if id is not None:
        dict['_id'] = id
    
    dict['username'] = fake.name() 
    dict['gender'] = random.choice(['Male','Female', 'Unknown'])  
    dict['race'] = random.choice(["White","Black","Hispanic","Other","Asian","American Indian", "Biracial","White [Hispanic or Latino]","White/Black [Hispanic or Latino]","Unknown"])
    dict['criminal_history_category'] = random.choice(["Category I", "Category II","Category III","Category IV","Category V","Category VI"])
    
    return dict 


def getSentence(id=None):
    dict = {}
    if id is not None:
        dict['_id'] = id
          
    dict['minimum_probation_months'] = random.randint(5,18)
    dict['fine_dollars'] = random.randint(5,18)
    dict['minimum_fine_dollars'] = random.randint(500,8000)
    dict['community_service_hours'] = random.randint(24,180)
    return dict 


def getCharge(sentence_list, id=None):
    dict = {}
    if id is not None:
        dict['_id'] = id
        
    dict['trial_type'] = random.choice(["Guilty plea","Trial by judge","Trial by jury"])
    dict['charge_code'] = random.choice(["Antitrust","Armed Robbery","Armed Violence","Arson"]) 
    dict['primary'] = random.choice([True, False])  
    dict['attempted'] = random.choice([True, False]) 
    dict['possible_sentences'] = sentence_list

    return dict 


def getCase(attorney_id, client_id, charges_list, id=None):
    dict = {}
    if id is not None:
        dict['_id'] = id

    dict['attorney_id'] = attorney_id
    dict['client_id'] = client_id    
    dict['possible_charges'] = charges_list

    return dict


def main():
    

    ACCOUNT = os.getenv('AGGREGATOR_DB_ACCOUNT')
    API_KEY = os.getenv('AGGREGATOR_DB_API_KEY')
    
    logging.info('Connecting to Cloudant DB')
    try:
        client = Cloudant.iam(ACCOUNT, API_KEY, connect=True) 
    except Exception as e:
        logging.error(e)
        sys.exit(1)

    logging.info('Creating Attorney DB')
    attorney_db = client.create_database("attorney") 
    attorney_docs = []
    for i in range(0, total_attorneys):
        attorney_docs.append(getAttorney(str(i)))        
    
    logging.info('Populating Attorney DB')
    attorney_db.bulk_docs(attorney_docs)
        
    logging.info('Creating Client DB')
    client_db = client.create_database('client') 
    client_docs = []
    for i in range(0, total_clients):
        client_docs.append(getClient(str(i)))

    logging.info('Populating Client DB')
    client_db.bulk_docs(client_docs)

    sentence_db = client.create_database('sentence')
    charge_db = client.create_database('charge')

    sentence_id = 0
    charge_id = 0
    sentence_docs = []
    charge_docs = []
    case_docs = []

    logging.info('Creating Case DB')
    case_db = client.create_database('case') 
    for i in range(0, total_cases):

        charges_list = []
        total_charges = random.randint(2,5)
        for j in range(1, total_charges):
            sentence_list = []
            for k in range (0, 2):
                sentence_id += 1
                sentence_list.append(str(sentence_id))
                sentence_docs.append(getSentence(str(sentence_id)))
                
            charge_id += 1
            charges_list.append(str(charge_id))
            charge_docs.append(getCharge(sentence_list, str(charge_id)))


        attorney_id = str(random.randint(0, total_attorneys))
        client_id = str(random.randint(0, total_clients))
        case_docs.append(getCase(attorney_id=attorney_id, client_id=client_id, charges_list=charges_list, id=str(i)))
    
    logging.info('Populating Case DB')
    case_db.bulk_docs(case_docs)

    logging.info('Populating Charge DB')
    charge_db.bulk_docs(charge_docs)

    logging.info('Populating Sentence DB')
    sentence_db.bulk_docs(sentence_docs)   

    try:
        logging.info('Disconnecting from Cloudant database')
        client.disconnect()
        sys.exit(0)
    except Exception as e:
        logging.error(e)
        sys.exit(1)


main()
