import { RxStompConfig } from '@stomp/rx-stomp';
import { environment } from '@env/environment';

export const CustomRxStompConfig: RxStompConfig = {
    // Which server?
    brokerURL: `ws://${environment.apiHost}/ws`,

    // Headers
    // Typical keys: login, passcode, host
    connectHeaders: {
        'Authorization': 'Bearer ' + localStorage.getItem('token'),
    },

    beforeConnect: (stompClient: any): void => {
        const token = localStorage.getItem('token');
        stompClient.connectHeaders = {
            'Authorization': 'Bearer ' + token,
        };
    },

    // How often to heartbeat?
    // Interval in milliseconds, set to 0 to disable
    heartbeatIncoming: 0, // Typical value 0 - disabled
    heartbeatOutgoing: 20000, // Typical value 20000 - every 20 seconds

    // Wait in milliseconds before attempting auto reconnect
    // Set to 0 to disable
    // Typical value 500 (500 milli seconds)
    reconnectDelay: 0,

    // Will log diagnostics on console
    // It can be quite verbose, not recommended in production
    // Skip this key to stop logging to console
    debug: (msg: string): void => {
        console.log(new Date(), msg);
    },
};
