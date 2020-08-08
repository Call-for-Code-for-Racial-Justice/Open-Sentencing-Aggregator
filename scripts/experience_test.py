import os, time, sys, datetime
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.common.keys import Keys

# Do an action on the app's landing page
options = Options()
options.add_argument('--headless')
options.add_argument('--no-sandbox')
options.add_argument('--disable-dev-shm-usage')
driver = webdriver.Chrome(options=options)
driver.get(os.environ["APP_URL"]); # Open a browser to the app's landing page
time.sleep(3)

# Verify the expected content is present
# TODO change this when we change the landing page
for x in range(0, 3): #We have found that sometimes Java Liberty on Knative needs a refresh once or twice to boot and show the correct landing  page
    driver.refresh()
    time.sleep(3)
    title_text = driver.title
    print("The title text is: {}".format(title_text))
    if title_text == "IBM Cloud Starter":
        print("Experience Test Successful")
        sys.exit(0)
    print("Experience Test Failed: unexpected title text {}. Retrying.".format(title_text))
    time.sleep(27)

sys.exit("Experience Test Failed after 4 trys: unexpected title text {}".format(title_text))
