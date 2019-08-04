package kr.lindol.uml;

/**
 * @startuml
 * 
 * interface MyIntentServiceContractView {
 * 	+showSummary(phoneNumber:String, information:String)
 * 	+closeSummary()
 * 	+showErrorForNotFoundPhoneNumber(phoneNumber:String)
 * }
 * 
 * interface MyIntentServiceContractPresenter
 * {
 * 	+handleIncomingCall(phoneNumber:String, state:String)
 * }
 * 
 * class MyIntentServicePresenter {
 * 	-mView:MyIntentServiceContractView
 * 	-mModel:PhoneNumberModel
 * 	+MyIntentServicePresenter(view:MyIntentServiceContractView)
 * }
 * 
 * class MyIntentService {
 * 	-mPresenter:MyIntentServiceContractPresenter
 * }
 * 
 * class PhoneNumberModel {
 * 	-mPhoneBookData:HashMap<String, String>
 * 	+contains(phoneNumber:String):boolean
 * 	+get(phoneNumber:String):String
 * 	+add(phoneNumber:String, extraData:String):boolean
 * }
 * 
 * MyIntentServicePresenter ..|> MyIntentServiceContractPresenter
 * MyIntentService ..|> MyIntentServiceContractView
 * 
 * MyIntentService --> MyIntentServiceContractPresenter
 * MyIntentServicePresenter --> MyIntentServiceContractView
 * MyIntentServicePresenter --> PhoneNumberModel
 * 
 * class IncomingCallReceiver {
 * 
 * }
 * 
 * class PhoneNumberView {
 * 
 * }
 * 
 * @enduml
 */
public class PhoneBookUml {

}
