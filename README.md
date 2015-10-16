Installation
===
- Install vagrant and Virtualbox 5+
- clone GIT repo
- vagrant box add SOMEPATH/se7en-vX.box se7en-vX --name se7en-v2
- change the name in the Vagrantfile and delete .vagrant folder
- vagrant up

Updating VM
===
- vagrant halt
- vagrant package --output se7en-vX+1.box
- deploy BOX file to dropbox