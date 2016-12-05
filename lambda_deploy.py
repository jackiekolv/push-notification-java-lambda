# Copyright 2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file
# except in compliance with the License. A copy of the License is located at
#
#     http://aws.amazon.com/apache2.0/
#
# or in the "license" file accompanying this file. This file is distributed on an "AS IS"
# BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
# License for the specific language governing permissions and limitations under the License.
"""
A Bitbucket Builds template for deploying an application to AWS Lambda
joshcb@amazon.com
v1.0.0
"""
from __future__ import print_function
import os
import sys
import boto3
from botocore.exceptions import ClientError

def publish_new_version(artifact):
    """
    Publishes new version of the AWS Lambda Function
    """
    try:
        client = boto3.client('lambda')
    except ClientError as err:
        print("Failed to create boto3 client.\n" + str(err))
        return False

    try:
        response = client.update_function_code(
            FunctionName=os.getenv('AWS_LAMBDA_FUNCTION_NAME'),
            ZipFile=open('/opt/atlassian/pipelines/agent/build/target/push-notification-java-lambda.jar', 'rb').read(),
            Publish=True
        )
        print(artifact)
        print("Successfully updated function code.\n")
        print (repr(response))
        return response
    except ClientError as err:
        print("Failed to update function code.\n" + str(err))
        return False
    except IOError as err:
        print("Failed to access " + artifact + ".\n" + str(err))
        return False

def main():
    " Your favorite wrapper's favorite wrapper "
    if not publish_new_version('/opt/atlassian/pipelines/agent/build/target/push-notification-lambda.jar'):
        sys.exit(1)

if __name__ == "__main__":
    main()
