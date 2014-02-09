rpu
===

Tool for Updating Released Products

## Level1

Find the list of branches which have a specific file to be updated.

## Level2

Commit to multi branches using Git plumbing commands.

ex) git mcommit <SourceFile> <TargetFile> <Msg>

1. Get target branches from Level1
2. Start loop
    1. git checkout branch[i]
    2. replace contents of <TargetFile> with <SourceFile>
    3. git add .
    4. git commit -m <Msg>
3. End loop
4. Verify all SHA's of the <TargetFile> are equal.
    1. 모든 브랜치의 git cat-file -p <TreeOfTargetFile>을 통해 검증
 