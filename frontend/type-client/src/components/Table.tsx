import React, {useState, useEffect} from 'react';
// @ts-ignore
import axios from 'axios';

/**
 * Functional component with the table displayed and options to make changes to it.
 * @param props the name of the selected table (from the dropdown)
 */
function Table(props : {selectedTable : string, rowToInsert : Map<string, string>, setRowToInsert: (m : Map<string,string>) => void}) {
    const [columnNames, setColumnNames] = useState<string[]>([])
    const [tableData, setTableData] = useState([[]])
    const HOST_URL: string = "http://localhost:4567"

    const config = {
        headers: {
            "Content-Type": "application/json",
            'Access-Control-Allow-Origin': '*',
        }
    };

    /**
     * Used to display the header row.
     */
    const displayHeaderRow = (columnNames : string[]) => {

        return (
            <tr>
            {columnNames.map(header => (
                <th>
                    {header}

                </th>
            ))}
            </tr>
        )
    }

    /**
     * Used to display the rest of the rows (excluding the header row).
     */
    const displayRows = (tableData : string[][]) => {
        return (
            tableData.map(row => {
                return(
                    <tr>
                        {row.map(rowValue => (
                            <td>
                                {rowValue}
                            </td>
                        ))}
                    </tr>
                )}))
    }

    /**
     * Inserts a row to the table.
     * @param row to insert
     */
    const insertValue = (row : Map<string, string>) => {
        let requestBody = "{\"tableName\":\"" + props.selectedTable + "\","
        let newColumnNames = Array.from(row.keys())

        for (let i = 0; i < newColumnNames.length-1; i++) {
            requestBody +=  " \"" + newColumnNames[i] + "\":\"" + row.get(newColumnNames[i]) + "\","
        }
        requestBody += " \"" + newColumnNames[newColumnNames.length-1] + "\":\"" + row.get(newColumnNames[newColumnNames.length-1]) + "\"}"

        if (props.selectedTable !== "" && props.rowToInsert.size == 5) {
            axios.post(HOST_URL + "/insert", requestBody, config)
                .then((response: any) => {
                    if(response.data['columnNames'] != null && response.data['rowData'] != null){
                        setColumnNames(response.data['columnNames'])
                        setTableData(response.data['rowData'])
                        props.setRowToInsert(new Map())
                    }
                })
                .catch((error: any) => {
                    console.log(error);
                });
        }
    }

    // Loads the table when the inserted row changes.
    useEffect(() => {if (props.rowToInsert !== new Map()) {insertValue(props.rowToInsert)}}, [props.rowToInsert])

    return (
      <div >
          <br/>
          <table id="leaderboard" style={{
              display: 'flex',
              alignItems: 'center',
              justifyContent: 'center',
          }}> <tbody>
              {displayHeaderRow(columnNames)}
              {displayRows(tableData)}</tbody>
          </table>
          <br/>
      </div>
  );
}

export default Table;