Installation
===
- Install Vagrant and Virtualbox 5+
- Clone this repo
- vagrant box add SOMEPATH/se7en-v6.box --name se7en-v6
- vagrant up

Exporting new version of the VM
===
- vagrant halt
- vagrant package --output se7en-v7.box
- Upload the new .box to the Google Drive folder
