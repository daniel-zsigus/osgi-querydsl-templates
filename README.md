querydsl-sqltemplates-ecm
=========================

The Everit - QueryDSL Templates module contains two Components that makes
it possible to create SQLTemplates instances via configuration.

 - SQLTemplatesComponent: It is possible to select the database type via
   configuration.
 - AutoSQLTemplatesComponent: This component needs a DataSource. Based on
   the connection metadata it decides which type of SQLTemplates it should
   instantiate.

To see the all the configuration possibilities, deploy the module to your
OSGi container and check the configuration possibilities in webconsole.

[![Analytics](https://ga-beacon.appspot.com/UA-15041869-4/everit-org/osgi-querydsl-templates)](https://github.com/igrigorik/ga-beacon)
