@echo off
chcp 65001

git init

git config user.name "wanderingverse"
git config user.email "lh19890781942@126.com"
git remote add origin https://github.com/wanderingverse/study-notebook.git

git status
git pull origin master
git add *

for /f "tokens=2 delims==" %%I in ('"wmic os get localdatetime /value"') do set datetime=%%I
set year=%datetime:~0,4%
set month=%datetime:~4,2%
set day=%datetime:~6,2%
set commit_date=%year%年%month%月%day%日

git commit -m "%commit_date%"
git push origin master
git status

pause
