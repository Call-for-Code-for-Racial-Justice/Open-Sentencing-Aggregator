#### Populate Cloudant Database

This application includes a python script for populating the backend Cloudant database with mock data using the Faker framework. 

To run the this script, first you need to [install Python](https://www.python.org/downloads/) on your system.

Then install the following libraries
```bash
pip install Faker
pip install cloudant
pip install ibmcloudant
pip install --upgrade "ibmcloudant>=0.0.43"
```

Modify server.env file located at /src/main/liberty/config/server.env by adding your Cloudant Account name and API key. You may refer to these [docs] 
(https://www.ibm.com/support/pages/how-do-you-determine-cloudant-account-name-ibm-cloud#:~:text=To%20find%20out%20the%20Cloudant,returned%20list%20of%20service%20credentials) to look up your account name and api key.
   

Usage:
```bash
python populate_db.py -h


usage: populate_db.py [-h] [-total_attorneys TOTAL_ATTORNEYS] [-total_clients TOTAL_CLIENTS] [-total_cases TOTAL_CASES] [-env_file ENV_FILE]

optional arguments:
  -h, --help            show this help message and exit
  -total_attorneys TOTAL_ATTORNEYS
                        Number of new attorneys to add to the database
  -total_clients TOTAL_CLIENTS
                        Number of new clients to add to the database
  -total_cases TOTAL_CASES
                        Number of new cases to add to the database
  -env_file ENV_FILE    Path to env file
```

Run the script with default parameters
```bash
python populate_db.py
```
The script will lookup your Cloudant credentials from server.env file. It will add 5 databases - attorney, client, case, charge, sentence - to your Cloudant account. The databases attorney, client and case will have 5 records each. The databases charge and sentence will contain a random number of records. 


To run the script without default parameters
```bash
python populate_db.py -total_attorneys=<number of attorneys> -total_clients=<number of clients> -total_cases=<number of cases> -env_file=<path/to/your/.env/file>
```


