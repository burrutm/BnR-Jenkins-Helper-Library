def call(Map config = [:]){
	config.configuration = config.configuration  ?: 'Simulation'
    powershell(script: "python '${GetResources()}/scripts/CreateArSimInstallation.py' --project '${config.project}' --configuration '${config.configuration}' --simulationDirectory '${config.project}/ArSim'");
    powershell(script: "python '${GetResources()}/scripts/StartArSim.py' --simulationDirectory '${config.project}/ArSim'");
    powershell(returnStdout: true, script: "pytest '${config.integrationTestDir}' --junit-xml='${config.project}/IntegrationTestResults/results.xml' --alluredir='${config.project}/AllureReport' --suppress-tests-failed-exit-code");
    powershell(script: "python '${GetResources()}/scripts/StopArSim.py' --simulationDirectory '${config.project}/ArSim'");
}