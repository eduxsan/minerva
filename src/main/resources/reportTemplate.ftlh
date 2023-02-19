<#-- this is a freemarker template -->
<html>
  <head>
    <title>${testreport.name} Test Report</title>
    <style type="text/css">
        table
        {
          table-layout:fixed;
          width:100%;
          word-break: break-word;
        }
        .dataBase
        {
        	border: 1px solid black;
            word-break: break-word;
            border-collapse: collapse;
        }
        .index
        {
            width: 20px;
        }
    </style>
  </head>
  <body bgcolor="20b2aa">
  <div style="background-color: #20b2aa">
    <table BORDER="1" bordercolor="FDFEFE" style="width:100%; height: 25%" align="center" bgcolor="20b2aa">
        <tr>
             <th colspan="100%" align="center"><img src="irhythm.png" alt="iRhythm"></th>
        </tr>
        <tr>
             <th colspan="25%">${testreport.name} Test Report</th>
             <th colspan="25%">Bamboo Build ID:  ${testreport.info}</th>
             <th colspan="25%"><a href="${testreport.repo}">Repository</a></th>
             <th colspan="25%">${testreport.date}</th>
        </tr>
        <tr>
             <th colspan="100%">Execution Summary</th>
        </tr>
        <tr>
             <th colspan="20%">WS Service Version</th>
             <th colspan="20%">WS Build</th>
             <th colspan="20%">Total Test Cases</th>
             <th colspan="20%"><strong><font color="green">Pass</font>/<font color="red">Fail</font> Test Cases</strong></th>
             <th colspan="20%"><strong><font color="green">Pass</font>/<font color="red">Fail</font> Rate</strong></th>
        </tr>
        <tr>
             <td colspan="20%" align="center">${testreport.version! "unknown"}</td>
             <td colspan="20%" align="center">${testreport.build! "unknown"}</td>
             <td colspan="20%" align="center">${testreport.totalTest}</td>
             <td colspan="20%" align="center"><strong><font color="green">${testreport.pass}</font>/<font color="red">${testreport.fail}</font></strong></td>
             <td colspan="20%" align="center"><strong><font color="green">${testreport.passRate}%</font>/<font color="red">${testreport.failRate}%</font></strong></td>
        </tr>
        </table>
      <table style="width:30%">
      <tr>
                  <td colspan="10%"><b>Failed</b></td>
                  <td bgcolor="red" colspan=${testreport.failRate?number *0.2}%" height="20">${testreport.fail}</td>
                  </tr><tr>
                  <td colspan="10%"><b>Passed</b></td>
                  <td bgcolor="green" colspan=${testreport.passRate?number *0.2}%" height="20">${testreport.pass}</td>
      </tr><br>
      <table BORDER="1" bordercolor="20b2aa" bgcolor="FDFEFE" style="width:100%; height: 100%" align="left">
        <tr>
          <th colspan="10%" bgcolor="20b2aa" height="25">Test Name</th>
          <th colspan="12%" bgcolor="20b2aa" height="25">Expected Assertions</th>
          <th colspan="30%" bgcolor="20b2aa" height="25">Raw Request</th>
          <th colspan="30%" bgcolor="20b2aa" height="25">Raw Response</th>
          <th colspan="15%" bgcolor="20b2aa" height="25">Description</th>
          <th colspan="3%" bgcolor="20b2aa" height="25" width="10%">Result</th>
        </tr>
        <#list testreport.testDetailsList?sort_by("result") as testdetail>
            <tr>
               <td colspan="10%" bgcolor="${testdetail.resultColor}">
                    <a href="${testdetail.jamaURL}">${testdetail.jamaId}</a>
                    ${testdetail.testName}
               </td>
               <td colspan="72%">
               <table BORDER="1" bordercolor="FDFEFE">
               <#list testdetail.steps as step>
                    <tr>
                        <td colspan="1">
                        <div class=scrollable>
                             <#list step.assertions as assertion>
                             ${assertion.reason} <br>
                             Expected: ${assertion.expected! "<null>"} <br>
                             Actual: ${assertion.actual! "<null>"} <br><br>
                             </#list>
                        </div>
                        </td>
                        <td colspan="3">
                        <div class=scrollable>
                            <#if step.splunkLogs??>
                                <u><b>STEP: ${step.stepName}</u></b><br>
                                <i>Earliest_time</i>: ${step.earliestTime! "<null>"} <br>
                                <i>Latest_time</i>: ${step.latestTime! "<null>"} <br>
                                <i>Search value:</i>: ${step.search! "<null>"} <br>
                            </#if>
                            <#if step.httpStatus??>
                                <u><b>STEP: ${step.stepName}</u></b><br>
                                <i>Username</i>: ${step.username! "<null>"} <br>
                                <i>Password</i>: ${step.password! "<null>"} <br>
                                <i>Endpoint</i>: ${step.endpoint! "no endpoint"} <br>
                                <i>Request Body</i>: ${step.requestBody} <br> <br>
                            </#if>
                            <#if (step.queryResults?? || step.queryRowsResults??)>
                                <u><b>STEP: ${step.stepName}</u></b><br>
                                <i>DataBase query parameters</i>: <br><br>
                                <i>DataBase</i>: ${step.dataBase! "<null>"} <br>
                                <i>Table</i>: ${step.table! "<null>"} <br>
                                <i>Search Parameter</i>: ${step.searchParameter! "<null>"} <br><br>
                                <i>Query</i>: ${step.query! "<null>"} <br>
                            </#if>
                        </div>
                        </td>
                        <td colspan="3">
                        <div class=scrollable>
                           <u><b>STEP NAME: ${step.stepName}</u></b><br>
                           <#if step.headers??>
                                <#list step.headers as header>
                                ${header}<br>
                                </#list>
                            </#if>
                            <#if step.httpStatus??>
                              <b>Http status line</b>: ${step.httpStatus} <br><br>
                            </#if>
                            <#if step.responseBody??>
                                <u><b>Response Body :</u></b> <br>
                                ${step.responseBody! "No Response Body"} <br> <br>
                            </#if>
                           <#if step.queryResults??>
                              <u><b>Columns-values from the query : </u></b> <br>
                              <ol type="1">
                              <#list step.queryResults as queryResult>
                                <li> ${queryResult.column}
                                  </br>----------------------</br>
                                  ${queryResult.value!} </li></br>
                              </#list>
                              </ol>
                           </#if>
                           <#if step.splunkLogs??>
                             <u><b>Rows result : </u></b> <br>
                             <ol type="1">
                                <#list step.splunkLogs.results as log>
                                  <li> ${log}
                                  </br></br>
                                </#list>
                             </ol>
                           </#if>
                           <#if step.queryRowsResults??>
                             <u><b>Row  -  Column -  Value from the query : </u></b> <br>
                             <table class="dataBase">
                                <tr>
                                    <th class="dataBase index"></th>
                                <#list step.queryRowsResults[0] as queryResult>
                                    <th class="dataBase">${queryResult.column}</td>
                                </#list>
                                </tr>
                                <#list step.queryRowsResults as queryRowResult>
                                  <tr>
                                      <th class="dataBase index">${queryRowResult?index}</th>
                                  <#list queryRowResult as queryResult>
                                      <td class="dataBase">${queryResult.value!} </td>
                                  </#list>
                                  </tr>
                                </#list>
                             </table>
                           </#if>
                        <#list testdetail.screenshots as screenshot>
                             <center><img src="screenshots/${screenshot}" height="20%" width="100%"></center><br>
                        </#list>
                        </div>
                        </td>
                    </tr>
                    </#list>
                    </table>
               </td>
               <td colspan="15%">
                    <p>${testdetail.description! "no description"}</p>
               </td>
               <td colspan="3%" bgcolor="${testdetail.resultColor}">
                    ${testdetail.result}
               </td>
            </tr>
        </#list>
      </table>
    </div>
  </body>
</html>