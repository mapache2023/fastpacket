const URL_WS = 'http://localhost:8084/api/servicio/enviosEspeciales/web/';
const tablaPaquetes =document.getElementById("infoPaquete")
const tablaCambios =document.getElementById("historialCambios")

async function obtenerEnvios(numeroGuia) {
    const infoEnvio = document.getElementById("informacion-personal");
	
	
    const infoPaquete = document.getElementById("tbodyPaquetes");
    const tbodyCambios = document.getElementById("tbodyCambios");

    // Limpiar las secciones
    infoEnvio.innerHTML = '<p>Cargando información del envío...</p>';
    tbodyPaquetes.innerHTML = '<p>Cargando paquetes...</p>';
    tbodyCambios.innerHTML = '<p>Cargando historial...</p>';

    try {
        const respuesta = await fetch(`${URL_WS}${numeroGuia}`, {
            method: 'GET'
        });

        if (respuesta.ok) {
            if(respuesta.status === 204){
		alert("No se encontro el envio")
		}else{
		const envio = await respuesta.json();
            mostrarInfoEnvio(envio, infoEnvio);
            mostrarPaquete(envio, tbodyPaquetes);
            mostrarCambios(envio, tbodyCambios);
		}			
        }else {
            throw new Error(`Error al obtener los envíos: ${respuesta.status}`);
        }
    } catch (error) {
        console.error('Error al consultar el envío:', error);
        infoEnvio.innerHTML = '<p>Lo sentimos, hubo un error al consultar los envíos.</p>';
       tablaCambios.innerHTML = '';
        tablaPaquetes.innerHTML = '';
    }
}

function mostrarInfoEnvio(datosEnvio, infoEnvio) {
    infoEnvio.innerHTML = ''; // Limpiar la lista actual
    
   if (!datosEnvio) {
        infoEnvio.innerHTML = '<p>No se encontró el envío.</p>';
    } else {
        const estadoEnvio = datosEnvio.estado ? datosEnvio.estado : 'Estado no Disponible'; // Verifica si 'estado' está definido
        const conductorEnvio = datosEnvio.conductor ? datosEnvio.conductor : 'Conductor no Asignado'; 
        const datos = `
            <tr><th><b>Conductor: </b></th><td>${conductorEnvio}</td></tr>
            <br>
            <tr><th><b>Costo: </b></th><td>${datosEnvio.costo}</td></tr>
            <br>
            <tr><th><b>Origen: </b></th><td>${datosEnvio.origenDireccion}</td></tr>
            <br>
            <tr><th><b>Destino: </b></th><td>${datosEnvio.destinoDireccion}</td></tr>
            <br>
            <tr><th><b>Estado del Envío: </b></th><td>${estadoEnvio}</td></tr>
            <br>
            <tr><th><b>Número de Guía: </b></th><td>${datosEnvio.numeroGuia}</td></tr>
        `;
        infoEnvio.innerHTML = datos;
    }
}

// Función para insertar los datos del Paquete
function mostrarPaquete(datosEnvio, infoPaquete) {
    infoPaquete.innerHTML = ''; // Limpiar la lista actual
    let paqueteHtml = '';

    if (datosEnvio.paquetes.length === 0) {
        tablaPaquetes.innerHTML = '<p>No se encontraron paquetes para este envío.</p>';
    } else {
        datosEnvio.paquetes.forEach(paquete => {
            paqueteHtml += `
                    <tr>
                        <td>${paquete.descripcion}</td>
                        <td>${paquete.alto} cm</td>
                        <td>${paquete.ancho} cm</td>
                        <td>${paquete.profundidad} cm</td>
                        <td>${paquete.peso} kg</td>
                    </tr>
            `;
        });
        infoPaquete.innerHTML = paqueteHtml;
    }
}

// Función para insertar los cambios en el historial
function mostrarCambios(datosEnvio, tbodyCambios) {
    tbodyCambios.innerHTML = ''; // Limpiar la lista de cambios
    let cambiosHtml = '';

    if (datosEnvio.cambios.length === 0) {
         tablaCambios.innerHTML = '<p>No se encontraron cambios para este envío.</p>';
    } else {
        datosEnvio.cambios.forEach(cambio => {
            const fechaFormateada = formatFechaYHora(cambio.fechaCambio);
            cambiosHtml += `
                <tr>
                    <td>${cambio.colaborador}</td>
                    <td>${cambio.comentario}</td>
                    <td>${cambio.estado}</td>
                     <td>${fechaFormateada}</td>
                </tr>
            `;
        });
        tbodyCambios.innerHTML = cambiosHtml;
    }
}

function formatFechaYHora(fecha) {
    const opciones = { 
        year: 'numeric', 
        month: '2-digit', 
        day: '2-digit', 
        hour: '2-digit', 
        minute: '2-digit', 
    };
    const date = new Date(fecha);
    return date.toLocaleString('es-MX', opciones); // 'es-MX' para el formato mexicano
}
