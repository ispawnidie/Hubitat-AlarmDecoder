/**
 *  AlarmDecoder shared event message parser
 *
 *  Copyright 2016-2019 Nu Tech Software Solutions, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 * Shared by the AlarmDecoder network appliance driver and the AlarmDecoder service
 * SmartApp to parse Hubitat's comma-separated LAN event description strings
 * (UPnP discovery responses and local HTTP request/response metadata) into a map.
 *
 * To use this in the Hubitat UI, create it under Apps Code -> Libraries with
 * namespace "alarmdecoder" and name "alarmdecoderEventParser", then reference it
 * from a driver/app with: #include alarmdecoder.alarmdecoderEventParser
 */
library(
    base: "driver",
    author: "Nu Tech Software Solutions, Inc.",
    category: "Convenience",
    description: "Shared LAN event description parser for the AlarmDecoder app and driver",
    name: "alarmdecoderEventParser",
    namespace: "alarmdecoder",
    documentationLink: ""
)

def parseEventMessage(String description) {
    def event = [:]
    def parts = description.split(',')

    parts.each { part ->
        part = part.trim()
        if (part.startsWith('devicetype:')) {
            def valueString = part.split(":")[1].trim()
            event.devicetype = valueString
        }
        else if (part.startsWith('mac:')) {
            def valueString = part.split(":")[1].trim()
            if (valueString) {
                event.mac = valueString
            }
        }
        // If we made the request we will get the requestId of the host we contacted.
        // If we did not provide one in HubAction() then it will be auto generated
        // ex. c089d06f-ba3c-4baa-a1a4-950b9ffd372a
        else if (part.startsWith('requestId:')) {
            part -= "requestId:"
            def valueString = part.trim()
            if (valueString) {
                event.requestId = valueString
            }
        }
        // If we made the request we will get the IP of the host we contacted.
        else if (part.startsWith('ip:')) {
            part -= "ip:"
            def valueString = part.trim()
            if (valueString) {
                event.ip = valueString
            }
        }
        // If we made the request we will get the PORT of the host we contacted.
        else if (part.startsWith('port:')) {
            part -= "port:"
            def valueString = part.trim()
            if (valueString) {
                event.port = valueString
            }
        }
        else if (part.startsWith('networkAddress:')) {
            def valueString = part.split(":")[1].trim()
            if (valueString) {
                event.ip = valueString
            }
        }
        else if (part.startsWith('deviceAddress:')) {
            def valueString = part.split(":")[1].trim()
            if (valueString) {
                event.port = valueString
            }
        }
        else if (part.startsWith('ssdpPath:')) {
            part -= "ssdpPath:"
            def valueString = part.trim()
            if (valueString) {
                event.ssdpPath = valueString
            }
        }
        else if (part.startsWith('ssdpUSN:')) {
            part -= "ssdpUSN:"
            def valueString = part.trim()
            if (valueString) {
                event.ssdpUSN = valueString
            }
        }
        else if (part.startsWith('ssdpTerm:')) {
            part -= "ssdpTerm:"
            def valueString = part.trim()
            if (valueString) {
                event.ssdpTerm = valueString
            }
        }
        else if (part.startsWith('headers:')) {
            part -= "headers:"
            def valueString = part.trim()
            if (valueString) {
                event.headers = valueString
            }
        }
        else if (part.startsWith('body:')) {
            part -= "body:"
            def valueString = part.trim()
            if (valueString) {
                event.body = valueString
            }
        }
    }

    event
}
