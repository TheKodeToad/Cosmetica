git checkout 1.19
git merge master
pause
git push
REM ==============================
git checkout 1.19.1
git merge 1.19
pause
git push
REM ==============================
git checkout 1.19.3
git merge 1.19.1
pause
git push
REM ==============================
REM BACKPORTS
git checkout 1.18.1
git merge master
pause
git push
REM ==============================
git checkout 1.17
git merge 1.18.1
pause
git push
REM ==============================
git checkout 1.16.5
git merge 1.17
pause
git push
REM ==============================
git checkout master