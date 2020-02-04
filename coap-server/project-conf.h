#ifndef PROJECT_CONF_H_
#define PROJECT_CONF_H_

#undef NETSTACK_CONF_RDC
#define NETSTACK_CONF_RDC 	nullrdc_driver

// TABLES /*****************************/

#undef NBR_TABLE_CONF_MAX_NEIGHBORS 
#define NBR_TABLE_CONF_MAX_NEIGHBORS   	10		// Default: 15

#undef UIP_CONF_MAX_ROUTES 
#define UIP_CONF_MAX_ROUTES	      		20		// Default: 30
/**************************************/

#undef UIP_CONF_TCP 
#define UIP_CONF_TCP	0						// Disable TCP

#endif