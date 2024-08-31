xquery version "1.0" encoding "utf-8";

(:: OracleAnnotationVersion "1.0" ::)

declare namespace ns2="http://TargetNamespace.com/GetAddrress_getAddrress_response";
(:: import schema at "Resources/response_address.xsd" ::)
declare namespace ns1="http://xmlns.oracle.com/pcbpel/adapter/db/CustomSQL";
(:: import schema at "Resources/CustomSQL.xsd" ::)

declare variable $response as element()+ (:: schema-element(ns1:CustomSQLOutputCollection)+ ::) external;

declare function local:func($response as element()+ (:: schema-element(ns1:CustomSQLOutputCollection)+ ::)) as element() (:: schema-element(ns2:Root-Element) ::) {
    <ns2:Root-Element>
        <ns2:firstName>{fn:data($response/ns1:CustomSQLOutput[1]/ns1:firstName)}</ns2:firstName>
        <ns2:lastName>{upper-case($response/ns1:CustomSQLOutput[1]/ns1:lastName)}</ns2:lastName>
        <ns2:age>{fn:data($response/ns1:CustomSQLOutput[1]/ns1:age)}</ns2:age>
    </ns2:Root-Element>
};

local:func($response)