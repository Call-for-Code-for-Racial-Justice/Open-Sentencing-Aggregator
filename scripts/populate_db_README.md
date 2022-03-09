#### Populate Cloudant Database

This application includes a python script for populating the backend Cloudant database with mock data using the [faker framework](https://github.com/joke2k/faker/). 

To run the this script, first you need to install [Python](https://www.python.org/downloads/), [pip](https://pypi.org/project/pip/) and [pipenv](https://pypi.org/project/pipenv/) on your system.

Install the dependencies using pipenv
```bash
pipenv install
```

Use the following command if you already have installed the dependencies and want to activate the virtual environment only.
```bash
pipenv shell
```

Modify server.env file located at /src/main/liberty/config/server.env by adding your Cloudant Account name and API key. You may refer to this [documentation](https://www.ibm.com/support/pages/how-do-you-determine-cloudant-account-name-ibm-cloud#:~:text=To%20find%20out%20the%20Cloudant,returned%20list%20of%20service%20credentials) to look up your account name and api key.
   

Usage:
```bash
python populate_db.py -h


usage: populate_db.py [-h] [-total_attorneys TOTAL_ATTORNEYS] [-total_clients TOTAL_CLIENTS] [-env_file ENV_FILE]

optional arguments:
  -h, --help            show this help message and exit
  -total_attorneys TOTAL_ATTORNEYS
                        Number of new attorneys to add to the database
  -total_clients TOTAL_CLIENTS
                        Number of new clients to add to the database
  -env_file ENV_FILE    Path to env file
```

Run the script with default parameters
```bash
python populate_db.py
```
The script will lookup your Cloudant credentials from server.env file. It will add 2 databases - outcarcerate-attorney, outcarcerate-client - to your Cloudant account. Each database will consist 5 records.  


To run the script without default parameters
```bash
python populate_db.py -total_attorneys=<number of attorneys> -total_clients=<number of clients> -env_file=<path/to/your/.env/file>
```

Here is a sample output in JSON format for an attorney
```bash
{
	'_id': '0',
	 'username': 'Antonio Cook',
	 'cases': [
		 {
			 'attorney_id': '0',
			 'client_id': '0',
			 'possible_charges': [
            {
              'trial_type': 'Trial by jury',
              'charge_code': 'Antitrust',
              'primary': True,
              'attempted': False,
              'possible_sentences': [
                  {
                    'minimum_probation_months': 16,
                    'fine_dollars': 5,
                    'minimum_fine_dollars': 4056,
                    'community_service_hours': 118,
                    'sentence_type': 'Probation Terminated Satisfactorily',
                    'charge_disposition': 'FNPC'
                  },
                  {
                    'minimum_probation_months': 17,
                    'fine_dollars': 14,
                    'minimum_fine_dollars': 3448,
                    'community_service_hours': 73,
                    'sentence_type': 'Inpatient Mental Health Services',
                    'charge_disposition': 'FNG'
                  }
              ]
            },
            {
              'trial_type': 'Trial by judge',
              'charge_code': 'Theft by Deception',
              'primary': False,
              'attempted': True,
              'possible_sentences': [
                  {
                    'minimum_probation_months': 10,
                    'fine_dollars': 17,
                    'minimum_fine_dollars': 5721,
                    'community_service_hours': 70,
                    'sentence_type': 'Probation Only',
                    'charge_disposition': 'BFW'
                  },
                  {
                    'minimum_probation_months': 17,
                    'fine_dollars': 7,
                    'minimum_fine_dollars': 3586,
                    'community_service_hours': 56,
                    'sentence_type': 'Jail',
                    'charge_disposition': 'Verdict Guilty'
                  }
              ]
            },
            {
              'trial_type': 'Trial by jury',
              'charge_code': 'Tampering',
              'primary': False,
              'attempted': False,
              'possible_sentences': [
                  {
                    'minimum_probation_months': 7,
                    'fine_dollars': 16,
                    'minimum_fine_dollars': 803,
                    'community_service_hours': 173,
                    'sentence_type': 'Jail',
                    'charge_disposition': 'Death Suggested-Cause Abated'
                  },
                  {
                    'minimum_probation_months': 9,
                    'fine_dollars': 11,
                    'minimum_fine_dollars': 7109,
                    'community_service_hours': 64,
                    'sentence_type': 'Probation Terminated Instanter',
                    'charge_disposition': 'FNPC'
                  }
              ]
            }	
			 ]
		 }
	 ]
 }

```


