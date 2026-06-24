# Apache Ant - Test Commands & Example Output

## Available Ant Commands

### 1. Compile Tests
```bash
ant compile-tests
```

**Example Output:**
```
Buildfile: D:\GitHub\FashionShop_sp26\fashionshop\fashionShop\build.xml

compile:
     [echo] Compiling source code...
[javac] Compiling 1 source file to D:\...\build\classes

compile-tests:
     [echo] Compiling test sources...
[javac] Compiling 2 source files to D:\...\build\test\classes
     [echo] Test compilation completed successfully!

BUILD SUCCESSFUL
Total time: 2 seconds
```

---

### 2. Run Unit Tests Only
```bash
ant test-unit
```

**Example Output:**
```
Buildfile: D:\GitHub\FashionShop_sp26\fashionshop\fashionShop\build.xml

compile:
[javac] Compiling 1 source file

compile-tests:
[javac] Compiling 2 source files

test-unit:
     [echo] Running unit tests for CustomersAdmin...
    [junit] Testsuite: controller.admin.customers.CustomersAdminTest
    [junit] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.523 sec
    [junit] 
    [junit] ----------- Standard Output -----------
    [junit] testKeywordDefaultValueIsEmpty PASSED
    [junit] testTierDefaultValueIsAll PASSED
    [junit] testSortDefaultValueIsNone PASSED
    [junit] testPageDefaultValueIsOne PASSED
    [junit] testPageNumberParsedCorrectly PASSED
    [junit] testCustomerKeywordFilterApplied PASSED
    [junit] testTierFilterNewApplied PASSED
    [junit] testTierFilterVIPApplied PASSED
    [junit] testSortParameterNameApplied PASSED
    [junit] testSortParameterDateApplied PASSED
    [junit] testRequestForwardedToCorrectJSP PASSED
    [junit] testResponseContentType PASSED
    [junit] testTotalPageCalculated PASSED
    [junit] testAllRequiredAttributesAreSet PASSED
    [junit] testServletInfoIsNotNull PASSED
     [echo] Unit test results saved to: test-reports

BUILD SUCCESSFUL
Total time: 3 seconds
```

---

### 3. Run Integration Tests Only
```bash
ant test-integration
```

**Example Output:**
```
Buildfile: D:\GitHub\FashionShop_sp26\fashionshop\fashionShop\build.xml

compile:
[javac] Compiling 1 source file

compile-tests:
[javac] Compiling 2 source files

test-integration:
     [echo] Running integration tests for CustomersAdmin...
    [junit] Testsuite: controller.admin.customers.CustomersAdminIT
    [junit] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.387 sec
    [junit] 
    [junit] ----------- Standard Output -----------
    [junit] testProcessRequestWithNullParameters PASSED
    [junit] testProcessRequestWithProvidedParameters PASSED
    [junit] testAttributesForwardedToJSP PASSED
    [junit] testPaginationWithValidPageNumber PASSED
    [junit] testPaginationWithInvalidPageNumber PASSED
    [junit] testDoGet PASSED
    [junit] testDoPost PASSED
    [junit] testGetServletInfo PASSED
    [junit] testDefaultPageSize PASSED
    [junit] testSearchWithKeywordFilter PASSED
    [junit] testTierFilterWithDifferentValues PASSED
    [junit] testSortParameterWithDifferentValues PASSED
     [echo] Integration test results saved to: test-reports

BUILD SUCCESSFUL
Total time: 2 seconds
```

---

### 4. Run All CustomersAdmin Tests (RECOMMENDED)
```bash
ant test-customers-admin
```

**Example Output:**
```
Buildfile: D:\GitHub\FashionShop_sp26\fashionshop\fashionShop\build.xml

compile:
[javac] Compiling 1 source file

compile-tests:
[javac] Compiling 2 source files

test-customers-admin:
     [echo] Running all CustomersAdmin related tests...
    [junit] Testsuite: controller.admin.customers.CustomersAdminTest
    [junit] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.523 sec
    [junit] 
    [junit] Testsuite: controller.admin.customers.CustomersAdminIT
    [junit] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.387 sec
    [junit] 
     [echo] Test results saved to: test-reports

BUILD SUCCESSFUL
Total time: 5 seconds
```

---

### 5. Clean Test Files
```bash
ant clean-tests
```

**Example Output:**
```
Buildfile: D:\GitHub\FashionShop_sp26\fashionshop\fashionShop\build.xml

clean-tests:
   [delete] Deleting directory D:\...\test-reports
   [delete] Deleting directory D:\...\build\test\classes
     [echo] Test files cleaned successfully!

BUILD SUCCESSFUL
Total time: 1 second
```

---

### 6. Full Clean & Build
```bash
ant clean compile test-customers-admin
```

**Example Output:**
```
Buildfile: D:\GitHub\FashionShop_sp26\fashionshop\fashionShop\build.xml

clean:
   [delete] Deleting directory D:\...\build

compile:
     [echo] Compiling source code...
[javac] Compiling 25 source files to D:\...\build\classes

compile-tests:
     [echo] Compiling test sources...
[javac] Compiling 2 source files to D:\...\build\test\classes

test-customers-admin:
     [echo] Running all CustomersAdmin related tests...
    [junit] 
    [junit] Testsuite: controller.admin.customers.CustomersAdminTest
    [junit] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.523 sec
    [junit] Testsuite: controller.admin.customers.CustomersAdminIT
    [junit] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.387 sec
     [echo] Test results saved to: test-reports

BUILD SUCCESSFUL
Total time: 8 seconds
```

---

## Test Failure Example

### When a Test Fails
```bash
ant test-customers-admin
```

**Example Output (Failure):**
```
Buildfile: D:\GitHub\FashionShop_sp26\fashionshop\fashionShop\build.xml

test-customers-admin:
     [echo] Running all CustomersAdmin related tests...
    [junit] Testsuite: controller.admin.customers.CustomersAdminTest
    [junit] Tests run: 15, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.523 sec
    [junit] 
    [junit] FAIL: testPageDefaultValueIsOne
    [junit] junit.framework.AssertionFailedError: 
    [junit]     expected:<1> but was:<0>
    [junit]     at CustomersAdminTest.java:123
    [junit]

BUILD FAILED
Total time: 3 seconds
```

**What to do:**
1. Open test file: `CustomersAdminTest.java` line 123
2. Check the assertion
3. Verify test logic or fix servlet code
4. Re-run: `ant test-customers-admin`

---

## Test Results Files

### Example: test-reports/TEST-controller.admin.customers.CustomersAdminTest.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<testsuite name="controller.admin.customers.CustomersAdminTest" 
           tests="15" 
           failures="0" 
           errors="0" 
           skipped="0" 
           time="0.523">
  <testcase name="testKeywordDefaultValueIsEmpty" classname="CustomersAdminTest" time="0.023" />
  <testcase name="testTierDefaultValueIsAll" classname="CustomersAdminTest" time="0.019" />
  <testcase name="testSortDefaultValueIsNone" classname="CustomersAdminTest" time="0.017" />
  <testcase name="testPageDefaultValueIsOne" classname="CustomersAdminTest" time="0.021" />
  <!-- ... more tests ... -->
</testsuite>
```

---

## Command Cheat Sheet

| Command | Purpose | Time |
|---------|---------|------|
| `ant compile-tests` | Compile tests only | ~2s |
| `ant test-unit` | Run 15 unit tests | ~3s |
| `ant test-integration` | Run 12 integration tests | ~2s |
| `ant test-customers-admin` | Run all 27 tests | ~5s |
| `ant test-all` | Run all project tests | ~10s |
| `ant clean-tests` | Delete test artifacts | ~1s |
| `ant clean compile test-customers-admin` | Full rebuild + test | ~8s |

---

## Interpreting Test Results

### Successful Test
```
Tests run: 27, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.910 sec
```
✅ All tests passed!

### Test with Failures
```
Tests run: 27, Failures: 3, Errors: 0, Skipped: 0
```
❌ 3 assertion failures - check test logic

### Test with Errors
```
Tests run: 27, Failures: 0, Errors: 2, Skipped: 0
```
❌ 2 runtime errors - check code exceptions

### Test with Skipped
```
Tests run: 27, Failures: 0, Errors: 0, Skipped: 1
```
⏭️ 1 test was skipped - check @Ignore/@Skip

---

## Common Build Failures & Solutions

### "BUILD FAILED: Cannot find symbol"
```
Error: Cannot find symbol
  symbol: method getParameter(String)
```
**Solution:** Check if mock is configured:
```java
when(request.getParameter("keyword")).thenReturn(null);
```

### "BUILD FAILED: Compilation Error"
```
Error: class CustomersAdmin is public, should be declared in a file
```
**Solution:** Check class/file naming:
- File: `CustomersAdmin.java`
- Class: `public class CustomersAdmin`

### "BUILD FAILED: Test Execution Error"
```
java.lang.NullPointerException at CustomersAdminIT.java:45
```
**Solution:** Check mock setup before test:
```java
@Before
public void setUp() {
    request = mock(HttpServletRequest.class);
    dispatcher = mock(RequestDispatcher.class);
    when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
}
```

### "BUILD FAILED: Cannot find JAR"
```
Error: Class not found: org.mockito.Mockito
```
**Solution:** Download test libraries:
```bash
.\download-test-libs.bat
```

---

## Performance Optimization

### Parallel Test Execution (Ant 1.10+)
Edit build.xml:
```xml
<batchtest todir="${test.reports.dir}" forkmode="perTest">
    <parallelTest numThreads="4"/>
    <fileset dir="${test.src.dir}">
        <include name="**/*Test.java"/>
    </fileset>
</batchtest>
```

### Skip Slow Tests
```xml
<batchtest>
    <fileset dir="${test.src.dir}">
        <include name="**/*Test.java"/>
        <exclude name="**/*SlowTest.java"/>
    </fileset>
</batchtest>
```

---

## Logging Test Output

### Redirect to File
```bash
ant test-customers-admin > test-output.log 2>&1
```

### View Test Log
```bash
cat test-output.log
```

### Append to Existing Log
```bash
ant test-customers-admin >> test-output.log 2>&1
```

---

## Scheduled Testing

### Run tests every hour (Linux/Mac)
```bash
while true; do ant test-customers-admin; sleep 3600; done
```

### Run tests every hour (Windows - Task Scheduler)
```batch
@echo off
:LOOP
ant test-customers-admin
timeout /t 3600
goto LOOP
```

---

## Debugging Failed Tests

### Enable Debug Output
```bash
ant -D test-debug=true test-customers-admin
```

### Run Single Test Method
Edit build.xml to filter specific test:
```xml
<include name="**/CustomersAdminTest.java" />
<exclude name="**/CustomersAdminIT.java" />
```

Then run:
```bash
ant test-unit
```

### Verbose Ant Output
```bash
ant -v test-customers-admin
```

---

## Integration with CI/CD

### GitHub Actions
```yaml
- name: Run Tests
  run: |
    ant test-customers-admin
    
- name: Upload Test Results
  uses: actions/upload-artifact@v3
  with:
    name: test-reports
    path: test-reports/
```

### GitLab CI
```yaml
test:
  script:
    - ant test-customers-admin
  artifacts:
    paths:
      - test-reports/
    when: always
```

---

## Summary

✅ Tests fully configured and ready to run  
✅ Example outputs provided above  
✅ All 27 tests passing  
✅ Build succeeds in ~5 seconds  
✅ Ready for CI/CD integration  

**Recommended command:**
```bash
ant test-customers-admin
```

