
  Feature: Create Issue APIs Tests with comment

@CreateStory
Scenario: Test Create Issue APIs
When I invoke the CreateIssueMetadata API
Then the response code should be 200
And I extract the projectId and issueTypeId
When i invoke the createIssueAPI 
Then the response code should be 201
And verify the story id is present in response 
And I add a comment to the created issue 

 