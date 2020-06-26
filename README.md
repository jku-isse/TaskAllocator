# TaskAllocator
Prototype developed to demonstrate the applicability of the approach.

### Pre-requisites
1. Python version [3.6.8]
2. Atlassian Plugin SDK version [8.0.16]
3. Tensorflow version [1.14.0]

### How-to
1. Clone the repository
```
$ git clone https://github.com/jku-isse/TaskAllocator.git
```

2. Place the model files in TaskAllocator/src/main/resources (Download from [resources.zip](https://github.com/jku-isse/TaskAllocator/releases/download/v1.0/weights.best.hdf5))

3. Replace the "PATHTOREPLACE" in the TestPython.py with the extracted directory

4. Change directory
```
$ cd TaskAllocator
```

5. Start using the Atlassian Plugin SDK by executing following commands:
```
$ atlas-run   -- installs this plugin into JIRA and starts it on http://localhost:2990/jira
$ atlas-clean -- removes files built from atlas-run
$ atlas-debug -- same as atlas-run, but allows a debugger to attach at port 5005
$ atlas-help  -- prints description for all commands in the SDK
```

6. Create new project-> Create new issue -> approximated role will be generated based on the given title of the issue
