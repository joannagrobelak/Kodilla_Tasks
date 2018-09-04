call runcrud.bat
if "%ERRORLEVEL%" == "0" goto openbrowser
echo.
echo An error occured! (could not open runcrud.bat)
goto fail

:openbrowser
call "C:\Program Files (x86)\Mozilla Firefox\firefox.exe" -new-window http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto end
echo.
echo An error occured! (could not open the browser)
goto fail

:fail
echo.
echo There were errors.

:end
echo.
echo Work is finished.